package fesma.nl.Payroll;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.util.Objects;

@Entity
class Employee {

    private @Id
    @GeneratedValue Long id;
    private String name;
    private String role;
    private String mail;
    private int age;

    Employee() {
    }

    Employee(String name, String role, String mail, int age) {
        this.name = name;
        this.role = role;
        this.mail = mail;
        this.age = age;
    }

    public Employee(Object bilboBaggins, String burglar) {
    }

    public Long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getRole() {
        return this.role;
    }
    public int getAge(){
        return this.age;
    }
    public String getMail() {return this.mail;}

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRole(String role) {
        this.role = role;
    }
    public void setAge(int age){this.age = age;}
    public void setMail(String mail){this.mail = mail;}


    @Override
    public boolean equals(Object o) {

        if (this == o)
            return true;
        if (!(o instanceof Employee))
            return false;
        Employee employee = (Employee) o;
        return Objects.equals(this.id, employee.id) && Objects.equals(this.name, employee.name)
                && Objects.equals(this.role, employee.role) && Objects.equals(this.age, employee.age) && Objects.equals(this.mail,employee.mail);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.name, this.role, this.age, this,mail);
    }

    @Override
    public String toString() {
        return "Employee{" + "id=" + this.id + ", name='" + this.name + '\'' + ", role='" + this.role + '\'' + "age=" + this.age + "mail=" + this.mail +'}';
    }
}
