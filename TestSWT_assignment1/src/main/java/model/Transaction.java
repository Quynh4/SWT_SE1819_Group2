package model;

public class Transaction {
    private Employee employee;
    private Item item;
    private Customer customer;

    public Transaction(Employee employee, Item item, Customer customer) {
        this.employee = employee;
        this.item = item;
        this.customer = customer;
    }

    public Transaction() {
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
