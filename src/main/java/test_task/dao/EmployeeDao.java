package test_task.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import test_task.model.Employee;

import java.util.List;

@Repository
public interface EmployeeDao extends CrudRepository<Employee, Long> {

    //TODO Get a list of employees receiving a salary greater than that of the boss
    @Query(
            value = "SELECT *\n" +
                    "FROM employee e,\n" +
                    "     employee b\n" +
                    "WHERE e.boss_id = b.id\n" +
                    "  AND e.salary > b.salary;",
            nativeQuery = true)
    List<Employee> findAllWhereSalaryGreaterThatBoss();

    //TODO Get a list of employees receiving the maximum salary in their department
    @Query(
            value = "SELECT *\n" +
                    "FROM employee e\n" +
                    "         JOIN (SELECT MAX(salary) highest, department_id FROM employee GROUP BY department_id) b\n" +
                    "              ON e.department_id = b.department_id AND e.salary = b.highest;",
            nativeQuery = true)
    List<Employee> findAllByMaxSalary();

    //TODO Get a list of employees who do not have boss in the same department
    @Query(
            value = "SELECT *\n" +
                    "FROM employee e\n" +
                    "         LEFT JOIN employee b on e.boss_id = b.id\n" +
                    "WHERE b.department_id IS NULL\n" +
                    "   OR b.department_id <> e.department_id;",
            nativeQuery = true)
    List<Employee> findAllWithoutBoss();
}
