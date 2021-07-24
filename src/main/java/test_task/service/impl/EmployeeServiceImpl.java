package test_task.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import test_task.dao.DepartmentDao;
import test_task.dao.EmployeeDao;
import test_task.model.Department;
import test_task.model.Employee;
import test_task.service.EmployeeService;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    EmployeeDao employeeDao;
    @Autowired
    DepartmentDao departmentDao;

    @Override
    public List<Employee> findAllBySalaryGreaterThatBoss() {
        return employeeDao.findAllWhereSalaryGreaterThatBoss();
    }

    @Override
    public List<Employee> findAllByMaxSalary() {
        return employeeDao.findAllByMaxSalary();
    }

    @Override
    public List<Employee> findAllWithoutBoss() {
        return employeeDao.findAllWithoutBoss();
    }

    @Override
    public Long fireEmployee(String name) {
        Iterable<Employee> employees = employeeDao.findAll();

        //TODO Implement method using Collection
        // ---write your code here
        Long id = 0L;
        Iterator<Employee> i = employees.iterator();
        while (i.hasNext()) {
            Employee e = i.next();
            if (e.getName().equals(name)) {
                id = e.getId();
                i.remove();
            }
        }
        employeeDao.saveAll(employees);
        return id;
    }

    @Override
    public Long changeSalary(String name) {
        Iterable<Employee> employees = employeeDao.findAll();

        //TODO Implement method using Collection
        // ---write your code here

        Long id = 0L;
        for (Employee e : employees) {
            if (e.getName().equals(name)) {
                id = e.getId();
                e.setSalary(new BigDecimal("0"));
            }
        }

        employeeDao.saveAll(employees);
        return id;
    }

    @Override
    public Long hireEmployee(Employee employee) {
        //TODO Implement method using Collection and DAO
        // ---write your code here

        String departmentName = "IT";
        Department department = getDepartment(departmentName);
        if (department != null) {
            Employee boss = betBossOfDepartment(department);
            if (boss != null) {
                employee.setSalary(new BigDecimal("2500"));
                employee.setName("John Doe");
                employee.setDepartment(department);
                employee.setBoss(boss);
                employeeDao.save(employee);
                return employee.getId();
            }
        }
        return 0L;
    }

    /**
     * @param department Department
     * @return ?Employee
     */
    protected Employee betBossOfDepartment(Department department) {
        for (Employee boss : employeeDao.findAllWithoutBoss()) {
            if (boss.getDepartment().getId().equals(department.getId())) {
                return boss;
            }
        }
        return null;
    }

    /**
     * @param name String
     * @return ?Department
     */
    protected Department getDepartment(String name) {
        for (Department department : departmentDao.findAll()) {
            if (department.getName().equals(name)) {
                return department;
            }
        }
        return null;
    }
}
