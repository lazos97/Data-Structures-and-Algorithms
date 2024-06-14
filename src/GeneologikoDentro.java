import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

public class GeneologikoDentro {

    private HashMap<String, Person> gTree;

    public GeneologikoDentro() {
        gTree = new HashMap<String, Person>();
    }

    public HashMap<String, Person> getgTree() {
        return gTree;
    }

    public void setgTree(HashMap<String, Person> gTree) {
        this.gTree = gTree;
    }

    public Person addPersonToTree(String name) {
        Person p;
        p = this.gTree.get(name);
        if (p == null) {
            p = new Person(name);
            this.gTree.put(name, p);
        }
        return p;
    }

    public void processInputLine(String[] data) {
        if (data.length >= 2) {
            String name = data[0].trim();
            String gender = data[1].trim();
            Person p = addPersonToTree(name);
            p.setGender(gender);

            System.out.println("Name: " + name + ", Gender: " + gender);
        } else if (data.length == 3) {
            String name1 = data[0].trim();
            String role = data[1].trim();
            String name2 = data[2].trim();

            Person p1 = addPersonToTree(name1);
            Person p2 = addPersonToTree(name2);

            setRelationship(p1, p2, role);
        }
    }

    public static void setRelationship(Person p1, Person p2, String relationship) {
        if (relationship.equalsIgnoreCase("spouse")) {
            p1.setSpouse(p2);
            p2.setSpouse(p1);
        } else if (relationship.equalsIgnoreCase("mother")) {
            if (p1.getGender().equalsIgnoreCase("woman")) {
                p2.getParents().add(p1);
                p1.getChildren().add(p2);
            } else {
                System.out.println("Invalid relationship: " + relationship + ". " + p1.getName() + " is not a woman.");
            }
        } else if (relationship.equalsIgnoreCase("father")) {
            if (p1.getGender().equalsIgnoreCase("man")) {
                p2.getParents().add(p1);
                p1.getChildren().add(p2);
            } else {
                System.out.println("Invalid relationship: " + relationship + ". " + p1.getName() + " is not a man.");
            }
        } else {
            System.out.println("Unknown relationship: " + relationship);
        }
    }

    public boolean isSpouse(Person p1, Person p2) {
        return p1.getSpouse() == p2 || p2.getSpouse() == p1;
    }

    public boolean isFather(Person p1, Person p2) {
        return p1.getGender().equalsIgnoreCase("man") && isParent(p1, p2);
    }

    public boolean isMother(Person p1, Person p2) {
        return p1.getGender().equalsIgnoreCase("woman") && isParent(p1, p2);
    }

    public boolean isParent(Person p1, Person p2) {
        return p1.getChildren().contains(p2);
    }

    public boolean isChild(Person p1, Person p2) {
        if (isFather(p2, p1) || isMother(p2, p1)) {
            return true;
        }
        return false;
    }

    public boolean isSibling(Person p1, Person p2) {
        HashSet<Person> parentsP1 = p1.getParents();
        HashSet<Person> parentsP2 = p2.getParents();

        return !p1.equals(p2) && !Collections.disjoint(parentsP1, parentsP2);
    }

    public boolean isSon(Person p1, Person p2) {
        return isChild(p1, p2) && p1.getGender().equalsIgnoreCase("man");
    }

    public boolean isDaughter(Person p1, Person p2) {
        return isChild(p1, p2) && p1.getGender().equalsIgnoreCase("woman");
    }

    public boolean isBrother(Person p1, Person p2) {
        return p1.getGender().equalsIgnoreCase("man") && isSibling(p1, p2);
    }

    public boolean isSister(Person p1, Person p2) {
        return p1.getGender().equalsIgnoreCase("woman") && isSibling(p1, p2);
    }

    public boolean isGrandfather(Person p1, Person p2) {
        // ο p1 είναι ο παππούς του p2 αν ένας γονιός του p2 είναι το παιδί του p1 και ο
        // p1 είναι man
        for (Person child : p1.getChildren()) {
            if (isParent(child, p2) && p1.getGender().equalsIgnoreCase("man")) {
                return true;
            }
        }
        return false;
    }

    public boolean isGrandmother(Person p1, Person p2) {
        // ο p1 είναι η γιαγιά του p2 αν ένας γονιός του p2 είναι το παιδί του p1 και ο
        // p1 είναι woman
        for (Person child : p1.getChildren()) {
            if (isParent(child, p2) && p1.getGender().equalsIgnoreCase("woman")) {
                return true;
            }
        }
        return false;
    }

    public boolean isGrandson(Person p1, Person p2) {
        return isGrandchild(p1, p2) && p1.getGender().equalsIgnoreCase("man");
    }

    public boolean isGranddaughter(Person p1, Person p2) {
        return isGrandchild(p1, p2) && p1.getGender().equalsIgnoreCase("woman");
    }

    public boolean isGrandchild(Person p, Person grandparent) {
        return isDescendant(p, grandparent);
    }

    private boolean isDescendant(Person p, Person ancestor) {
        for (Person child : ancestor.getChildren()) {
            if (child.equals(p) || isDescendant(p, child)) {
                return true;
            }
        }
        return false;
    }

    private HashSet<Person> getGrandparents(Person p) {
        // Μέθοδος για να πάρουμε τους παππούδες ενός προσώπου
        HashSet<Person> grandparents = new HashSet<>();
        for (Person parent : p.getParents()) {
            grandparents.addAll(parent.getParents());
        }
        return grandparents;
    }

    public boolean isCousin(Person p1, Person p2) {
        // Για να είναι ξαδέρφια πρέπει να έχουν τους ίδιους παππούδες
        HashSet<Person> grandparentsP1 = getGrandparents(p1);
        HashSet<Person> grandparentsP2 = getGrandparents(p2);

        return !Collections.disjoint(grandparentsP1, grandparentsP2);
    }

    public boolean isUncle(Person p1, Person p2) {
        // ο p1 είναι θείος του p2 όταν ο p1 είναι αδερφός ενός γονιού του p2
        return isSiblingOfParent(p1, p2);
    }

    public boolean isAunt(Person p1, Person p2) {
        return isSiblingOfParent(p1, p2);
    }

    private boolean isSiblingOfParent(Person potentialSibling, Person targetPerson) {
        for (Person parent : targetPerson.getParents()) {
            if (isSibling(potentialSibling, parent)) {
                return true;
            }
        }
        return false;
    }

    public boolean isNephew(Person p1, Person p2) {
        // o p1 είναι ανιψιός του p2 όταν ο p1 είναι υιός του αδερφού του p2
        for (Person sibling : getSiblingsOfParents(p2)) {
            if (isSon(p1, sibling) && !isChild(p2, p1)) {
                return true;
            }
        }
        return false;
    }

    public boolean isNiece(Person p1, Person p2) {
        for (Person sibling : getSiblingsOfParents(p2)) {
            if (isDaughter(p1, sibling) && !isChild(p2, p1)) {
                return true;
            }
        }
        return false;
    }

    public HashSet<Person> getSiblingsOfParents(Person p) {
        HashSet<Person> siblings = new HashSet<>();
        for (Person parent : p.getParents()) {
            siblings.addAll(parent.getChildren());
        }
        siblings.remove(p);
        return siblings;
    }

}
