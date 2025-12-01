# Bajaj Finserv Health – Java Qualifier (SQL Automation)

This project is a Spring Boot application built for the **Bajaj Finserv Health Java Qualifier**.  
The application automatically:

1. Sends a POST request to generate a webhook.
2. Receives a webhook URL + JWT access token.
3. Prepares the required **final SQL query**.
4. Submits the SQL query to the webhook using the token.
5. Prints the server response.

---

## 🚀 Tech Stack

- Java 17  
- Spring Boot 3  
- WebClient (Spring WebFlux)  
- Maven  
- REST API communication  
- JWT Authentication (Bearer Token)  

---

## 📂 Project Structure

---

## 📝 Final SQL Query Submitted

The application automatically submits the following SQL query:

```sql
SELECT
                      d.department_name,
                      ROUND(AVG(h.age), 2) AS average_age,
                      (
                        SELECT GROUP_CONCAT(CONCAT(t.first_name,' ',t.last_name) SEPARATOR ', ')
                        FROM (
                          SELECT DISTINCT e2.first_name, e2.last_name
                          FROM employee e2
                          JOIN payments p2 ON p2.emp_id = e2.emp_id
                          WHERE e2.department = d.department_id
                            AND p2.amount > 70000
                          ORDER BY e2.first_name, e2.last_name
                          LIMIT 10
                        ) AS t
                      ) AS employee_list
                    FROM department d
                    LEFT JOIN (
                      SELECT e.emp_id,
                             e.department,
                             TIMESTAMPDIFF(YEAR, e.dob, CURDATE()) AS age
                      FROM employee e
                      JOIN payments p ON p.emp_id = e.emp_id
                      WHERE p.amount > 70000
                      GROUP BY e.emp_id, e.department, e.dob
                    ) h ON h.department = d.department_id
                    GROUP BY d.department_id, d.department_name
                    ORDER BY d.department_id DESC;
