package controllers;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import domain.Employee;
import play.*;
import play.data.Form;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;
import play.mvc.*;
import views.html.*;

public class Application extends Controller {

    public static Result index() {
        return ok(index.render("Employee Mgmt App"));
    }
    
    @Transactional
    public static Result addEmployee() {
    
    	Employee employee = new Employee();
    	employee.setName(Form.form().bindFromRequest().get("name"));
    	employee.setSalary(Integer.parseInt(Form.form().bindFromRequest().get("salary")));
    	
    	EntityManager entityManager = JPA.em();
    	entityManager.persist(employee);
    	
    	return redirect(routes.Application.index());
    }
    
    @Transactional
    public static Result viewEmployees() {
    	
    	EntityManager entityManager = JPA.em();
        TypedQuery<Employee> query =
        		entityManager.createQuery("SELECT employee FROM Employee employee", Employee.class);
    		  List<Employee> results = query.getResultList();
    	return ok(viewEmployees.render(results));
    }
    
}
