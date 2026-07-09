# JPA Entity Lifecycle: Managed vs Unmanaged States

## Table of Contents

1. [Introduction to Entity States](#introduction-to-entity-states)
2. [The Persistence Context](#the-persistence-context)
3. [Entity States in Detail](#entity-states-in-detail)
4. [State Transitions](#state-transitions)
5. [EntityManager Operations](#entitymanager-operations)
6. [Practical Examples](#practical-examples)
7. [Common Scenarios](#common-scenarios)
8. [Best Practices](#best-practices)
9. [Troubleshooting](#troubleshooting)

---

## Introduction to Entity States

In JPA, every entity object can be in one of **four possible states**. Understanding these states is crucial for working effectively with JPA.

### The Four Entity States

```sketch
┌─────────────┐
│   NEW       │  ← Object created with 'new' keyword
│ (Transient) │     Not associated with persistence context
└─────────────┘     Not in database
       │
       │ persist()
       ↓
┌─────────────┐
│  MANAGED    │  ← Associated with persistence context
│(Persistent) │     Changes are tracked
└─────────────┘     Will be synchronized to database
       │
       │ detach() / close() / clear()
       ↓
┌─────────────┐
│  DETACHED   │  ← Was managed, now disconnected
│             │     Changes are NOT tracked
└─────────────┘     Can be reattached with merge()
       │
       │ remove()
       ↓
┌─────────────┐
│  REMOVED    │  ← Marked for deletion
│             │     Will be deleted on flush/commit
└─────────────┘
```

---

## The Persistence Context

### What is the Persistence Context?

The **Persistence Context** is like a "cache" or "first-level cache" that tracks managed entities.

**Key Characteristics:**
- Associated with an `EntityManager`
- Acts as a buffer between your Java objects and the database
- Tracks all changes to managed entities
- Synchronizes changes to database on flush/commit
- Each `EntityManager` has its own persistence context

**Visual Representation:**

```sketch
┌───────────────────────────────────────────────────┐
│           Your Application                        │
│                                                   │
│  Employee emp = new Employee("John", "Doe");      │
│  em.persist(emp);  // Entity enters PC            │
│  emp.setSalary(60000);  // Change tracked         │
└───────────────────────────────────────────────────┘
                    ↓
┌───────────────────────────────────────────────────┐
│        Persistence Context (EntityManager)        │
│                                                   │
│    ┌─────────────────────────────────────────┐    │
│    │  Tracked Entities (Managed)             │    │
│    │                                         │    │
│    │  Employee@123 {                         │    │
│    │    id: 1,                               │    │
│    │    firstName: "John",                   │    │
│    │    lastName: "Doe",                     │    │
│    │    salary: 60000  ← DIRTY (changed)     │    │
│    │  }                                      │    │
│    └─────────────────────────────────────────┘    │
└───────────────────────────────────────────────────┘
                    ↓ flush() / commit()
┌───────────────────────────────────────────────────┐
│               Database                            │
│                                                   │
│  UPDATE employee                                  │
│  SET salary = 60000                               │
│  WHERE id = 1;                                    │
└───────────────────────────────────────────────────┘
```

---

## Entity States in Detail

### 1. NEW / TRANSIENT State

**Definition:** Object is created but not yet associated with a persistence context.

**Characteristics:**
- ✓ Object exists in memory
- ✗ Not tracked by JPA
- ✗ No database representation
- ✗ No identifier assigned (unless manually set)
- ✗ Changes are not synchronized to database

**Example:**

```java
// Create a new employee
Employee employee = new Employee("John", "Doe", 50000);

// State: NEW (Transient)
System.out.println(employee.getId());  // null (no ID assigned yet)

// This employee is not in the database
// JPA doesn't know about it
// Changes won't be persisted
employee.setSalary(60000);  // Change is NOT tracked
```

**Testing State:**

```java
EntityManager em = emf.createEntityManager();

Employee employee = new Employee("John", "Doe", 50000);

// Check if entity is managed
boolean isManaged = em.contains(employee);
System.out.println("Is managed: " + isManaged);  // false
```

---

### 2. MANAGED / PERSISTENT State

**Definition:** Object is associated with a persistence context and changes are tracked.

**Characteristics:**
- ✓ Associated with persistence context
- ✓ Has a database identity (primary key)
- ✓ Changes are automatically tracked
- ✓ Changes synchronized to database on flush/commit
- ✓ Lazy-loaded associations can be accessed

**Example:**

```java
EntityManager em = emf.createEntityManager();
EntityTransaction tx = em.getTransaction();
tx.begin();

// Create and persist
Employee employee = new Employee("John", "Doe", 50000);
em.persist(employee);  // State: NEW → MANAGED

// Now it's managed!
System.out.println(employee.getId());  // ID is assigned (e.g., 1)
System.out.println(em.contains(employee));  // true

// Any changes are automatically tracked
employee.setSalary(60000);  // Change is tracked!
// No need to call em.update() or em.save()

tx.commit();  // Changes automatically written to database
em.close();
```

**How Changes are Tracked:**

```java
tx.begin();

Employee employee = em.find(Employee.class, 1L);  // State: MANAGED

// JPA takes a "snapshot" of the entity's state
// Original: salary = 50000

employee.setSalary(60000);  // Modify the entity

// On flush/commit, JPA compares current state with snapshot
// Detects change: 50000 → 60000
// Generates UPDATE statement automatically

tx.commit();
// SQL: UPDATE employee SET salary = 60000 WHERE id = 1
```

**Important:** You don't need explicit save/update methods! JPA tracks changes automatically.

---

### 3. DETACHED State

**Definition:** Object was previously managed but is no longer associated with a persistence context.

**Characteristics:**
- ✓ Has a database identity (was persisted before)
- ✗ Not tracked by JPA anymore
- ✗ Changes are NOT synchronized to database
- ✗ Lazy associations may cause `LazyInitializationException`
- ✓ Can be reattached using `merge()`

**How Entities Become Detached:**

1. **EntityManager is closed:**

```java
EntityManager em = emf.createEntityManager();
em.getTransaction().begin();

Employee employee = em.find(Employee.class, 1L);  // State: MANAGED
System.out.println(em.contains(employee));  // true

em.getTransaction().commit();
em.close();  // EntityManager closed

// State: MANAGED → DETACHED
System.out.println(em.contains(employee));  // IllegalStateException (EM is closed)

// Changes are NOT tracked anymore
employee.setSalary(70000);  // No effect on database!
```

2. **Explicit detach():**

```java
EntityManager em = emf.createEntityManager();
em.getTransaction().begin();

Employee employee = em.find(Employee.class, 1L);  // State: MANAGED
System.out.println(em.contains(employee));  // true

em.detach(employee);  // Explicitly detach

// State: MANAGED → DETACHED
System.out.println(em.contains(employee));  // false

employee.setSalary(70000);  // Change NOT tracked

em.getTransaction().commit();
em.close();
// No UPDATE statement generated
```

3. **clear() - Detaches all entities:**

```java
EntityManager em = emf.createEntityManager();
em.getTransaction().begin();

Employee emp1 = em.find(Employee.class, 1L);  // MANAGED
Employee emp2 = em.find(Employee.class, 2L);  // MANAGED

em.clear();  // Detach ALL entities

// Both are now DETACHED
System.out.println(em.contains(emp1));  // false
System.out.println(em.contains(emp2));  // false
```

4. **Transaction commit in some scenarios:**

```java
// With transaction-scoped persistence context
EntityManager em = emf.createEntityManager();
em.getTransaction().begin();

Employee employee = em.find(Employee.class, 1L);  // MANAGED

em.getTransaction().commit();  // In some contexts, entities become DETACHED here

// Depends on persistence context type (transaction-scoped vs extended)
```

**Detached Entities and Lazy Loading:**

```java
EntityManager em = emf.createEntityManager();

Employee employee = em.find(Employee.class, 1L);  // MANAGED
// department is lazy-loaded (not yet initialized)

em.close();  // State: MANAGED → DETACHED

// This will throw LazyInitializationException!
try {
    String deptName = employee.getDepartment().getName();
} catch (LazyInitializationException e) {
    System.out.println("Cannot access lazy association from detached entity!");
}
```

---

### 4. REMOVED State

**Definition:** Entity is marked for deletion and will be removed from database on flush/commit.

**Characteristics:**
- ✓ Still associated with persistence context
- ✓ Tracked by JPA
- ✓ Will be deleted from database on flush/commit
- ✗ Should not be used after removal

**Example:**

```java
EntityManager em = emf.createEntityManager();
EntityTransaction tx = em.getTransaction();
tx.begin();

Employee employee = em.find(Employee.class, 1L);  // State: MANAGED

em.remove(employee);  // State: MANAGED → REMOVED

System.out.println(em.contains(employee));  // true (still in persistence context)

tx.commit();  // DELETE statement executed
// SQL: DELETE FROM employee WHERE id = 1

em.close();

// State: REMOVED → DETACHED (after commit)
// The object still exists in memory but is detached
System.out.println(employee.getId());  // Still has ID (1)
```

**Important Notes:**

```java
// Cannot remove a detached entity
Employee detached = new Employee();
detached.setId(1L);  // Set ID manually

try {
    em.remove(detached);  // IllegalArgumentException!
} catch (IllegalArgumentException e) {
    System.out.println("Cannot remove detached entity!");
}

// Must merge first, then remove
Employee managed = em.merge(detached);  // DETACHED → MANAGED
em.remove(managed);  // MANAGED → REMOVED
```

---

## State Transitions

### Complete State Transition Diagram

```sketch
                    new Employee()
                          │
                          ↓
                    ┌──────────┐
                    │   NEW    │
                    └──────────┘
                          │
                          │ persist()
                          ↓
    ┌──────────────┬──────────┬─────────────────┐
    │   find()     │ refresh()│  JPQL query     │
    │   getReference()        │  fetch join     │
    └──────────────┴──────────┴─────────────────┘
                          │
                          ↓
                    ┌──────────┐
                    │ MANAGED  │←─────── merge()
                    └──────────┘
                       │  │  │
                       │  │  └──────→ remove() ──→ ┌─────────┐
                       │  │                         │ REMOVED │
                       │  │                         └─────────┘
                       │  │                              │
                       │  │                              │ commit/flush
                       │  │                              ↓
                       │  │                         (deleted from DB)
                       │  │
                       │  └──→ detach()
                       │        clear()
                       │        close()
                       │        rollback()
                       ↓
                 ┌──────────┐
                 │ DETACHED │
                 └──────────┘
                       │
                       └──→ merge() ──→ (back to MANAGED)
```

### Transition Methods Summary

| From State | To State | Method               | Description                          |
| ---------- | -------- | -------------------- | ------------------------------------ |
| NEW        | MANAGED  | `persist()`          | Make entity managed                  |
| NEW        | MANAGED  | `merge()`            | Create new managed entity (if no ID) |
| MANAGED    | DETACHED | `detach()`           | Detach single entity                 |
| MANAGED    | DETACHED | `clear()`            | Detach all entities                  |
| MANAGED    | DETACHED | `close()`            | Close EntityManager                  |
| MANAGED    | REMOVED  | `remove()`           | Mark for deletion                    |
| DETACHED   | MANAGED  | `merge()`            | Reattach entity                      |
| REMOVED    | -        | `commit()`/`flush()` | Delete from database                 |
| -          | MANAGED  | `find()`             | Load from database                   |
| -          | MANAGED  | `getReference()`     | Get proxy                            |
| -          | MANAGED  | JPQL/Criteria query  | Load via query                       |

---

## EntityManager Operations

### 1. persist()

**Purpose:** Makes a new (transient) entity managed.

**Effect:** NEW → MANAGED

```java
EntityManager em = emf.createEntityManager();
EntityTransaction tx = em.getTransaction();
tx.begin();

// Create new entity
Employee employee = new Employee("John", "Doe", 50000);
System.out.println("Before persist: " + em.contains(employee));  // false

// Make it managed
em.persist(employee);

System.out.println("After persist: " + em.contains(employee));  // true
System.out.println("ID assigned: " + employee.getId());  // e.g., 1

tx.commit();
// SQL: INSERT INTO employee (first_name, last_name, salary) VALUES ('John', 'Doe', 50000)
em.close();
```

**Important:**
- Entity MUST be in NEW state (not detached)
- ID is assigned (depending on generation strategy)
- Insert happens on flush/commit, not immediately

**With Cascade:**

```java
Department dept = new Department("IT");
Employee emp = new Employee("John", "Doe", 50000);
dept.addEmployee(emp);

em.persist(dept);  // If cascade is set, emp is also persisted
// Both department and employee are inserted
```

---

### 2. merge()

**Purpose:** Merges the state of a detached entity into the persistence context.

**Effect:** DETACHED → creates/updates MANAGED copy

```java
EntityManager em1 = emf.createEntityManager();
em1.getTransaction().begin();

Employee employee = em1.find(Employee.class, 1L);  // MANAGED
System.out.println("Original salary: " + employee.getSalary());  // 50000

em1.getTransaction().commit();
em1.close();  // State: MANAGED → DETACHED

// Modify detached entity
employee.setSalary(60000);

// Create new EntityManager
EntityManager em2 = emf.createEntityManager();
em2.getTransaction().begin();

// Merge detached entity
Employee managedEmployee = em2.merge(employee);

System.out.println("Same object? " + (employee == managedEmployee));  // false!
System.out.println(em2.contains(employee));  // false (original is still detached)
System.out.println(em2.contains(managedEmployee));  // true (copy is managed)

em2.getTransaction().commit();
// SQL: UPDATE employee SET salary = 60000 WHERE id = 1
em2.close();
```

**Key Points:**
- Returns a NEW managed copy
- Original object remains detached
- Use the returned object for further operations
- Works with both detached and new entities

**Common Pattern - Web Applications:**

```java
// Layer 1: Controller receives detached entity from web form
@PostMapping("/employee/update")
public String updateEmployee(@ModelAttribute Employee employee) {
    // employee is DETACHED (came from web form)
    
    // Layer 2: Service layer
    employeeService.update(employee);
    
    return "redirect:/employees";
}

// Service layer
@Transactional
public void update(Employee detachedEmployee) {
    // Merge detached entity
    Employee managed = em.merge(detachedEmployee);
    // Changes are tracked and persisted
}
```

---

### 3. remove()

**Purpose:** Marks an entity for deletion.

**Effect:** MANAGED → REMOVED

```java
EntityManager em = emf.createEntityManager();
EntityTransaction tx = em.getTransaction();
tx.begin();

Employee employee = em.find(Employee.class, 1L);  // MANAGED

em.remove(employee);  // MANAGED → REMOVED

System.out.println("Is contained: " + em.contains(employee));  // true

tx.commit();  // DELETE executed here
// SQL: DELETE FROM employee WHERE id = 1

em.close();
```

**Important:**
- Entity MUST be managed
- Actual deletion happens on flush/commit
- Cannot remove detached entities directly

**Removing Detached Entity:**

```java
// Wrong way
Employee detached = getDetachedEmployee();
em.remove(detached);  // IllegalArgumentException!

// Right way
Employee managed = em.merge(detached);
em.remove(managed);
```

**With Cascade:**

```java
@Entity
public class Department {
    @OneToMany(mappedBy = "department", cascade = CascadeType.REMOVE)
    private List<Employee> employees;
}

// Removing department also removes all employees
Department dept = em.find(Department.class, 1L);
em.remove(dept);
// SQL: DELETE FROM employee WHERE department_id = 1
// SQL: DELETE FROM department WHERE id = 1
```

---

### 4. find()

**Purpose:** Load an entity from database by primary key.

**Effect:** Database → MANAGED

```java
EntityManager em = emf.createEntityManager();

// Load employee with ID 1
Employee employee = em.find(Employee.class, 1L);
// SQL: SELECT * FROM employee WHERE id = 1

if (employee != null) {
    System.out.println("State: MANAGED");
    System.out.println(em.contains(employee));  // true
} else {
    System.out.println("Not found");
}

em.close();
```

**vs getReference():**

```java
// find() - Loads immediately
Employee emp1 = em.find(Employee.class, 1L);
// SQL executed immediately
System.out.println(emp1.getName());  // No additional SQL

// getReference() - Returns proxy (lazy)
Employee emp2 = em.getReference(Employee.class, 2L);
// No SQL yet!
System.out.println(emp2.getName());  // SQL executed now
```

---

### 5. detach()

**Purpose:** Detach a specific entity from persistence context.

**Effect:** MANAGED → DETACHED

```java
EntityManager em = emf.createEntityManager();
em.getTransaction().begin();

Employee employee = em.find(Employee.class, 1L);  // MANAGED
System.out.println("Before: " + em.contains(employee));  // true

em.detach(employee);  // MANAGED → DETACHED

System.out.println("After: " + em.contains(employee));  // false

employee.setSalary(70000);  // Not tracked!

em.getTransaction().commit();  // No UPDATE statement
em.close();
```

---

### 6. refresh()

**Purpose:** Reload entity state from database, discarding any changes.

**Effect:** MANAGED → MANAGED (reset to DB state)

```java
EntityManager em = emf.createEntityManager();
em.getTransaction().begin();

Employee employee = em.find(Employee.class, 1L);  // MANAGED
System.out.println("Original: " + employee.getSalary());  // 50000

employee.setSalary(60000);  // Modify
System.out.println("Modified: " + employee.getSalary());  // 60000

em.refresh(employee);  // Reload from database
System.out.println("After refresh: " + employee.getSalary());  // 50000 (change discarded!)

em.getTransaction().commit();
em.close();
// No UPDATE (changes were discarded)
```

---

### 7. flush()

**Purpose:** Synchronize persistence context to database immediately.

```java
EntityManager em = emf.createEntityManager();
em.getTransaction().begin();

Employee employee = new Employee("John", "Doe", 50000);
em.persist(employee);

// No SQL yet (depending on flush mode)

em.flush();  // Force synchronization
// SQL: INSERT INTO employee ... (executed NOW)

System.out.println("ID after flush: " + employee.getId());  // ID is assigned

// Continue with more operations...

em.getTransaction().commit();
em.close();
```

**Use Cases:**
- Force ID generation before commit
- Execute batch operations
- Get database-generated values
- Execute SQL in specific order

---

### 8. clear()

**Purpose:** Detach all entities from persistence context.

**Effect:** All MANAGED → DETACHED

```java
EntityManager em = emf.createEntityManager();
em.getTransaction().begin();

Employee emp1 = em.find(Employee.class, 1L);
Employee emp2 = em.find(Employee.class, 2L);
Employee emp3 = em.find(Employee.class, 3L);

System.out.println("All managed: " + em.contains(emp1));  // true

em.clear();  // Detach everything

System.out.println("All detached: " + em.contains(emp1));  // false

em.getTransaction().commit();
em.close();
```

**Use Case:**
- Clear memory when processing large datasets
- Reset persistence context

---

## Practical Examples

### Example 1: Web Application Flow (Detached Entities)

**Scenario:** User edits employee data in web form.

```java
// Step 1: Display form (GET request)
@GetMapping("/employee/{id}/edit")
public String editForm(@PathVariable Long id, Model model) {
    EntityManager em = emf.createEntityManager();
    
    Employee employee = em.find(Employee.class, id);  // MANAGED
    
    em.close();  // MANAGED → DETACHED
    
    model.addAttribute("employee", employee);  // Send detached entity to view
    return "employee-edit";
}

// Step 2: Process form submission (POST request)
@PostMapping("/employee/update")
public String update(@ModelAttribute Employee employee) {
    // employee is DETACHED (came from form with ID)
    
    EntityManager em = emf.createEntityManager();
    EntityTransaction tx = em.getTransaction();
    tx.begin();
    
    // Merge detached entity
    Employee managed = em.merge(employee);  // DETACHED → MANAGED
    
    tx.commit();  // Changes saved to database
    em.close();
    
    return "redirect:/employees";
}
```

---

### Example 2: Batch Processing with clear()

```java
EntityManager em = emf.createEntityManager();
EntityTransaction tx = em.getTransaction();
tx.begin();

List<Employee> employees = // ... get 10,000 employees from somewhere

for (int i = 0; i < employees.size(); i++) {
    Employee emp = employees.get(i);
    em.persist(emp);
    
    if (i % 50 == 0) {
        em.flush();  // Execute INSERT statements
        em.clear();  // Clear persistence context to free memory
    }
}

tx.commit();
em.close();
```

---

### Example 3: Optimistic Locking with Detached Entities

```java
@Entity
public class Product {
    @Id
    private Long id;
    
    private String name;
    private BigDecimal price;
    
    @Version
    private Long version;  // Optimistic locking
}

// User 1 loads product
EntityManager em1 = emf.createEntityManager();
Product product1 = em1.find(Product.class, 1L);  // version = 1
em1.close();  // Product is now DETACHED

// User 2 loads same product
EntityManager em2 = emf.createEntityManager();
Product product2 = em2.find(Product.class, 1L);  // version = 1
em2.close();  // Product is now DETACHED

// User 1 updates price
product1.setPrice(new BigDecimal("99.99"));
EntityManager em3 = emf.createEntityManager();
em3.getTransaction().begin();
em3.merge(product1);  // version = 1 → 2
em3.getTransaction().commit();  // Success!
em3.close();

// User 2 tries to update
product2.setPrice(new BigDecimal("89.99"));
EntityManager em4 = emf.createEntityManager();
em4.getTransaction().begin();
try {
    em4.merge(product2);  // version = 1 (stale!)
    em4.getTransaction().commit();
} catch (OptimisticLockException e) {
    em4.getTransaction().rollback();
    System.out.println("Concurrent modification detected!");
}
em4.close();
```

---

### Example 4: Transaction Rollback

```java
EntityManager em = emf.createEntityManager();
EntityTransaction tx = em.getTransaction();
tx.begin();

Employee employee = em.find(Employee.class, 1L);  // MANAGED
System.out.println("Original salary: " + employee.getSalary());  // 50000

employee.setSalary(60000);  // Change tracked

// Something goes wrong
try {
    // Some business logic that fails
    throw new RuntimeException("Error!");
} catch (Exception e) {
    tx.rollback();  // Rollback transaction
    System.out.println("Transaction rolled back");
}

em.close();

// Check database - salary is still 50000
EntityManager em2 = emf.createEntityManager();
Employee emp2 = em2.find(Employee.class, 1L);
System.out.println("Salary after rollback: " + emp2.getSalary());  // 50000
em2.close();
```

---

## Common Scenarios

### Scenario 1: LazyInitializationException

**Problem:**

```java
EntityManager em = emf.createEntityManager();

Employee employee = em.find(Employee.class, 1L);  // MANAGED
// department is @ManyToOne(fetch = FetchType.LAZY)

em.close();  // MANAGED → DETACHED

// This throws LazyInitializationException!
String deptName = employee.getDepartment().getName();
```

**Solutions:**

```java
// Solution 1: Access while managed
EntityManager em = emf.createEntityManager();
Employee employee = em.find(Employee.class, 1L);
String deptName = employee.getDepartment().getName();  // Access before close
em.close();

// Solution 2: Fetch join
EntityManager em = emf.createEntityManager();
Employee employee = em.createQuery(
    "SELECT e FROM Employee e JOIN FETCH e.department WHERE e.id = :id",
    Employee.class)
    .setParameter("id", 1L)
    .getSingleResult();
em.close();
String deptName = employee.getDepartment().getName();  // OK - already loaded

// Solution 3: Entity Graph
EntityManager em = emf.createEntityManager();
EntityGraph<Employee> graph = em.createEntityGraph(Employee.class);
graph.addAttributeNodes("department");
Employee employee = em.find(Employee.class, 1L,
    Collections.singletonMap("jakarta.persistence.fetchgraph", graph));
em.close();
String deptName = employee.getDepartment().getName();  // OK

// Solution 4: DTO projection (best for read-only)
EntityManager em = emf.createEntityManager();
String deptName = em.createQuery(
    "SELECT e.department.name FROM Employee e WHERE e.id = :id",
    String.class)
    .setParameter("id", 1L)
    .getSingleResult();
em.close();
```

---

### Scenario 2: Lost Updates

**Problem:**

```java
EntityManager em = emf.createEntityManager();
em.getTransaction().begin();

Employee employee = em.find(Employee.class, 1L);
em.detach(employee);  // Detach the entity

employee.setSalary(60000);  // Modify detached entity

em.getTransaction().commit();  // Changes NOT saved!
em.close();
```

**Solution:**

```java
EntityManager em = emf.createEntityManager();
em.getTransaction().begin();

Employee employee = em.find(Employee.class, 1L);
// Don't detach!

employee.setSalary(60000);  // Modify while managed

em.getTransaction().commit();  // Changes saved
em.close();

// OR merge if entity is detached
Employee detached = getDetachedEmployee();
em.getTransaction().begin();
Employee managed = em.merge(detached);
em.getTransaction().commit();
```

---

### Scenario 3: Incorrect merge() Usage

**Problem:**

```java
Employee detached = getDetachedEmployee();
detached.setSalary(60000);

em.getTransaction().begin();
em.merge(detached);  // Merge returns new managed instance!
detached.setSalary(70000);  // Modifying detached copy - no effect!
em.getTransaction().commit();

// Database has salary = 60000, not 70000!
```

**Solution:**

```java
Employee detached = getDetachedEmployee();
detached.setSalary(60000);

em.getTransaction().begin();
Employee managed = em.merge(detached);  // Use the returned instance!
managed.setSalary(70000);  // Modify the managed copy
em.getTransaction().commit();

// Database has salary = 70000 ✓
```

---

### Scenario 4: Removing Detached Entity

**Problem:**

```java
Employee detached = new Employee();
detached.setId(1L);  // Set ID manually

em.getTransaction().begin();
em.remove(detached);  // IllegalArgumentException!
em.getTransaction().commit();
```

**Solution:**

```java
Employee detached = new Employee();
detached.setId(1L);

em.getTransaction().begin();
Employee managed = em.merge(detached);  // Make it managed first
em.remove(managed);  // Now can remove
em.getTransaction().commit();

// Or simply
em.getTransaction().begin();
Employee managed = em.find(Employee.class, 1L);
em.remove(managed);
em.getTransaction().commit();
```

---

## Best Practices

### 1. Keep EntityManager Lifecycle Short

**❌ BAD - Long-lived EntityManager:**

```java
public class EmployeeService {
    private EntityManager em;  // Shared across multiple requests
    
    public EmployeeService() {
        this.em = emf.createEntityManager();  // Created once
    }
    
    public void updateEmployee(Employee employee) {
        em.getTransaction().begin();
        em.merge(employee);
        em.getTransaction().commit();
    }
}
```

**✅ GOOD - Request-scoped EntityManager:**

```java
public class EmployeeService {
    private EntityManagerFactory emf;
    
    public void updateEmployee(Employee employee) {
        EntityManager em = emf.createEntityManager();  // Create per request
        try {
            em.getTransaction().begin();
            em.merge(employee);
            em.getTransaction().commit();
        } finally {
            em.close();  // Always close
        }
    }
}
```

---

### 2. Use Transaction Properly

**Always use try-finally or try-with-resources:**

```java
EntityManager em = emf.createEntityManager();
EntityTransaction tx = em.getTransaction();
try {
    tx.begin();
    
    // Your business logic
    Employee emp = new Employee("John", "Doe", 50000);
    em.persist(emp);
    
    tx.commit();
} catch (Exception e) {
    if (tx.isActive()) {
        tx.rollback();
    }
    throw e;
} finally {
    em.close();
}
```

---

### 3. Avoid Mixing States

**❌ BAD:**

```java
Employee managed = em.find(Employee.class, 1L);
em.detach(managed);
managed.setSalary(60000);
// Forgot to merge - changes lost!
em.getTransaction().commit();
```

**✅ GOOD - Keep it simple:**

```java
Employee managed = em.find(Employee.class, 1L);
managed.setSalary(60000);  // Just modify while managed
em.getTransaction().commit();
```

---

### 4. Use DTOs for Read-Only Operations

**Instead of loading full entities:**

```java
// ❌ Loads entire entity graph
List<Employee> employees = em.createQuery(
    "SELECT e FROM Employee e JOIN FETCH e.department",
    Employee.class)
    .getResultList();

// ✅ Better - Use projection
List<EmployeeSummary> summaries = em.createQuery(
    "SELECT NEW com.example.EmployeeSummary(e.id, e.firstName, e.lastName, d.name) " +
    "FROM Employee e JOIN e.department d",
    EmployeeSummary.class)
    .getResultList();
```

---

### 5. Initialize Lazy Associations Before Detaching

```java
EntityManager em = emf.createEntityManager();

// Load with fetch join
Employee employee = em.createQuery(
    "SELECT e FROM Employee e " +
    "JOIN FETCH e.department " +
    "JOIN FETCH e.address " +
    "LEFT JOIN FETCH e.tasks " +
    "WHERE e.id = :id",
    Employee.class)
    .setParameter("id", 1L)
    .getSingleResult();

em.close();  // Now safe to detach

// All associations are loaded - no LazyInitializationException
System.out.println(employee.getDepartment().getName());
System.out.println(employee.getAddress().getZipCode());
employee.getTasks().forEach(t -> System.out.println(t.getName()));
```

---

## Troubleshooting

### Problem: Entity Changes Not Saved

**Symptoms:**
- You modify an entity but changes don't appear in database

**Possible Causes:**

1. **Entity is detached:**

```java
// Check
System.out.println(em.contains(employee));  // false = detached

// Solution: merge
Employee managed = em.merge(employee);
```

2. **No transaction:**

```java
// Missing transaction
Employee employee = em.find(Employee.class, 1L);
employee.setSalary(60000);
// No commit!

// Solution: Use transaction
em.getTransaction().begin();
Employee employee = em.find(Employee.class, 1L);
employee.setSalary(60000);
em.getTransaction().commit();
```

3. **Transaction rolled back:**

```java
// Check
if (tx.getRollbackOnly()) {
    System.out.println("Transaction marked for rollback!");
}
```

---

### Problem: LazyInitializationException

**Symptoms:**
- `org.hibernate.LazyInitializationException: could not initialize proxy - no Session`

**Cause:** Accessing lazy association from detached entity

**Solutions:** See Scenario 1 above

---

### Problem: OptimisticLockException

**Symptoms:**
- `jakarta.persistence.OptimisticLockException: Row was updated or deleted by another transaction`

**Cause:** Entity was modified by another transaction (version mismatch)

**Solution:**

```java
try {
    em.merge(employee);
    em.getTransaction().commit();
} catch (OptimisticLockException e) {
    em.getTransaction().rollback();
    
    // Reload and retry
    Employee fresh = em.find(Employee.class, employee.getId());
    // Merge changes or notify user
}
```

---

### Problem: Entity Not Updated After merge()

**Cause:** Not using the returned instance

```java
// Wrong
em.merge(detached);
detached.setSalary(70000);  // Detached copy not tracked!

// Correct
Employee managed = em.merge(detached);
managed.setSalary(70000);  // Managed copy is tracked
```

---

## Summary

### State Characteristics Quick Reference

| State        | Tracked? | Has ID? | In Database? | Can Modify?          |
| ------------ | -------- | ------- | ------------ | -------------------- |
| **NEW**      | ❌       | ❌      | ❌           | ✓ (not saved)        |
| **MANAGED**  | ✅       | ✅      | ✅           | ✓ (auto-saved)       |
| **DETACHED** | ❌       | ✅      | ✅           | ✓ (not saved)        |
| **REMOVED**  | ✅       | ✅      | ✅           | ⚠️ (will be deleted) |

### Key Takeaways

1. **Managed entities are automatically synchronized** - no explicit save needed
2. **Only managed entities can be tracked** - changes to detached entities require merge()
3. **EntityManager manages the persistence context** - close it when done
4. **Use merge() to reattach detached entities** - use the returned instance
5. **Initialize lazy associations before detaching** - prevents LazyInitializationException
6. **Keep EntityManager lifecycle short** - one per request/transaction
7. **Always use transactions** - changes require commit

---

**Related Guides:**
- `jpa-guide.md`: Complete JPA reference
- `jpa-relationships-guide.md`: Entity relationships
- `queries.md`: JPQL queries

**Last Updated:** 2024
