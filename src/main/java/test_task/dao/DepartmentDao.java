package test_task.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import test_task.model.Department;

import java.util.List;

@Repository
public interface DepartmentDao extends CrudRepository<Department, Long> {
    //TODO Get a list of department IDS where the number of employees doesn't exceed 3 people
    @Query(
            value = "SELECT d.id\n" +
                    "FROM employee e\n" +
                    "         INNER JOIN department d\n" +
                    "                    ON e.department_id = d.id\n" +
                    "GROUP BY d.id\n" +
                    "HAVING COUNT(*) <= 3;",
            nativeQuery = true)
    List<Long> findAllWhereDepartmentDoesntExceedThreePeople();

    //TODO Get a list of departments IDs with the maximum total salary of employees
    @Query(
            value = "SELECT department.id, total_amt\n" +
                    "FROM department,\n" +
                    "     (SELECT employee.department_id, SUM(employee.salary) total_amt\n" +
                    "      FROM employee\n" +
                    "      GROUP BY department_id) res\n" +
                    "WHERE res.department_id = department.id;",
            nativeQuery = true)
    List<Long> findAllByMaxTotalSalary();
}
