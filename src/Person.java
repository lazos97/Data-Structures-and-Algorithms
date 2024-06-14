import java.util.HashSet;

public class Person implements Comparable<Person> {

    private String name;
    private String gender;
    private Person spouse;
    private HashSet<Person> parents;
    private HashSet<Person> children;

    public Person(String name, String gender) {
        this.name = name;
        this.gender = gender;
        this.spouse = null;
        this.parents = new HashSet<Person>();
        this.children = new HashSet<Person>();
    }

    public Person(String name) {
        this.name = name;
        this.gender = "";
        this.spouse = null;
        this.parents = new HashSet<Person>();
        this.children = new HashSet<Person>();
    }

    public Person() {
        this.spouse = null;
        this.parents = new HashSet<Person>();
        this.children = new HashSet<Person>();
    }

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Person getSpouse() {
        return spouse;
    }

    public HashSet<Person> getParents() {
        return parents;
    }

    public HashSet<Person> getChildren() {
        return children;
    }

    public HashSet<Person> getSiblings() {
        HashSet<Person> siblings = new HashSet<>();

        for (Person parent : parents) {
            siblings.addAll(parent.getChildren());
        }

        siblings.remove(this);

        return siblings;
    }

    public void setSpouse(Person spouse) {
        this.spouse = spouse;
    }

    public void setParents(HashSet<Person> parents) {
        this.parents = parents;
    }

    public void setChildren(HashSet<Person> children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return "Person{" + "name=" + name + ", fyllo=" + gender + '}';
    }

    @Override
    public int compareTo(Person o) {
        return this.name.compareTo(o.name);
    }
}
