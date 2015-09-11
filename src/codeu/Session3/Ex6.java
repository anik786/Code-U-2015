package codeu.Session3;

import java.util.ArrayDeque;
import java.util.HashMap;

public class Ex6 {
    public static void main(String[] args) throws Exception{

        AncestorDatabase ancestorDatabase = new AncestorDatabase();
        ancestorDatabase.addPerson("A", "B", "C");
        ancestorDatabase.addPerson("B", "D", "E");
        ancestorDatabase.addPerson("C", "F", "G");
        ancestorDatabase.addPerson("D", "H", "I");
        ancestorDatabase.addPerson("E", "K", "J");
        ancestorDatabase.addPerson("F", "L", "M");
        ancestorDatabase.addPerson("X", "K", "M");
        ancestorDatabase.addPerson("G", "P", "Q");
        ancestorDatabase.addPerson("P", "R", "S");
//        ancestorDatabase.addPerson("#", "W", "A");


        System.out.println(ancestorDatabase.isAncestor("A", "B"));
        System.out.println(ancestorDatabase.isAncestor("A", "C"));
        System.out.println(ancestorDatabase.isAncestor("A", "D"));
        System.out.println(ancestorDatabase.isAncestor("A", "E"));
        System.out.println(ancestorDatabase.isAncestor("A", "F"));
        System.out.println(ancestorDatabase.isAncestor("A", "G"));
        System.out.println(ancestorDatabase.isAncestor("A", "H"));
        System.out.println(ancestorDatabase.isAncestor("A", "I"));
        System.out.println(ancestorDatabase.isAncestor("A", "J"));
        System.out.println(ancestorDatabase.isAncestor("A", "K"));
        System.out.println(ancestorDatabase.isAncestor("A", "L"));
        System.out.println(ancestorDatabase.isAncestor("A", "M"));
        System.out.println(ancestorDatabase.isAncestor("A", "N"));
        System.out.println(ancestorDatabase.isAncestor("A", "X"));

        ancestorDatabase.listRelationship("A", "G");
        ancestorDatabase.listRelationship("G", "A");

        ancestorDatabase.describeRelationship("A", "B");
        ancestorDatabase.describeRelationship("B", "A");
        ancestorDatabase.describeRelationship("A", "D");
        ancestorDatabase.describeRelationship("D", "A");
        ancestorDatabase.describeRelationship("A", "G");
        ancestorDatabase.describeRelationship("A", "P");
        ancestorDatabase.describeRelationship("A", "Q");
        ancestorDatabase.describeRelationship("Q", "A");
        ancestorDatabase.describeRelationship("A", "R");
        ancestorDatabase.describeRelationship("R", "A");
        ancestorDatabase.describeRelationship("A", "A");
        ancestorDatabase.describeRelationship("@", "A");

    }
}

class AncestorDatabase {
    private HashMap<String, Ancestor> tree = new HashMap<>();

    enum Gender {
        MALE, FEMALE, UNKNOWN
    }

    public void addPerson(String personsName, String mothersName, String fathersName) throws Exception {
        Ancestor mother = getOrAddAncestor(mothersName);
        Ancestor father = getOrAddAncestor(fathersName);
        Ancestor person = getOrAddAncestor(personsName);

        person.setFather(father);
        person.setMother(mother);
    }

    /**
     * Returns the ancestor node for given name. If ancestor does not exist, then a new ancestor is added
     * @param name Name of the ancestor
     * @return The ancestor node for the given name
     */
    private Ancestor getOrAddAncestor(String name) {
        Ancestor ancestor;
        if (!tree.containsKey(name)) {
            ancestor = new Ancestor(name);
            tree.put(name, ancestor);
        } else {
            ancestor = tree.get(name);
        }
        return ancestor;
    }

    //number of generations between the two
    public int isAncestor(String name, String ancestorName) {
        if (!tree.containsKey(name) || !tree.containsKey(ancestorName)) {
            // If one of the two people does not exits in the database
            return -1;
        }

        ArrayDeque<ArrayDeque<Ancestor>> generations = new ArrayDeque<>();
        // Set up first generation
        int depth = 0;
        generations.add(new ArrayDeque<>());
        generations.getFirst().add(tree.get(name));

        // Perform a BFS on generations, where each generation corresponds to a new depth
//        Can be done a lot faster through pre-computing depths when adding the nodes themselves
        while(!generations.isEmpty()) {
            ArrayDeque<Ancestor> generation = generations.removeFirst();

            for (Ancestor person : generation) {
                // Iterate through each person in this generation

                String personsName = person.getName();
                Ancestor mother = person.getMother();
                Ancestor father = person.getFather();


                if (personsName.equals(ancestorName)) { // Found ancestor
                    return depth;
                }

                // If the mother or father exists
                if (mother != null || father != null) {

                    // Select the next generation (or create a new one, if none exist)
                    ArrayDeque<Ancestor> nextGeneration;
                    if (generations.isEmpty()) {
                        nextGeneration = new ArrayDeque<>();
                        generations.add(nextGeneration);
                    } else {
                        nextGeneration = generations.getFirst();
                    }

                    // Add the father & mother of the current ancestor to the next generation if they exist in database
                    if (mother != null) {
                        nextGeneration.add(mother);
                    }
                    if (father != null) {
                        nextGeneration.add(father);
                    }
                }
            }
            depth++;
        }

        return -1;
    }

    public int isDescendant(String name1, String name2) {
        return isAncestor(name2, name1);
    }

    public void listRelationship(String name1, String name2) {
        if (!tree.containsKey(name1) || !tree.containsKey(name2)) {
            // If one of the two people does not exits in the database
            System.out.println("No Relationship!");
        }

        Ancestor person1 = tree.get(name1);
        Ancestor person2 = tree.get(name2);

        ArrayDeque<Ancestor> searchStack = new ArrayDeque<>();
        search(name2, searchStack, person1);

        if (searchStack.isEmpty()) {
            // If no relationship found, perform search the other way round
            search(name1, searchStack, person2);
        }

        if (searchStack.isEmpty()) {
            System.out.println("No Relationship!");
        } else {
            boolean first = true;
            for (Ancestor ancestor : searchStack) {
                if (!first) {
                    System.out.print("->");
                    first = false;
                } else {
                    first = false;
                }
                System.out.print(ancestor.getName());
            }
            System.out.println();
        }

    }

    private boolean search(String searchName, ArrayDeque<Ancestor> searchStack, Ancestor nextPerson) {
        if (nextPerson == null) {
            // No more people to search
            return false;
        }

        searchStack.addFirst(nextPerson);

        String personsName = nextPerson.getName();
        Ancestor mother = nextPerson.getMother();
        Ancestor father = nextPerson.getFather();

        if (personsName.equals(searchName) ||
                search(searchName, searchStack, mother) ||
                search(searchName, searchStack, father)) {
            return true;
        }

        searchStack.removeFirst();
        return false;
    }

    public void describeRelationship(String name1, String name2) {
        String relationship = "";
        String directRelation;
//        Ancestor person1 = tree.get(name1);
        Ancestor person2 = tree.get(name2);

        int generations = isAncestor(name1, name2);

        if (generations == 0) {
            System.out.println(name1 + " is " + name2);
            return;
        }

        if (generations != -1) {
//            if name2 is an ancestor of name1
            switch (person2.getGender()) {
                case MALE:
                    directRelation = "father";
                    break;
                case FEMALE:
                    directRelation = "mother";
                    break;
                default:
                    directRelation = "child";
                    break;
            }
        } else {
//            else check if name2 is a descendant of name1
            generations = isDescendant(name1, name2);

            if (generations == -1) {
//                If name1 and name2 is neither a descendant or ancestor of each other (i.e. no relationship)
                System.out.println(name1 + " and " + name2 + " has no known relationship!");
                return;
            }

            switch (person2.getGender()) {
                case MALE:
                    directRelation = "son";
                    break;
                case FEMALE:
                    directRelation = "daughter";
                    break;
                default:
                    directRelation = "child";
                    break;
            }
        }

        relationship += name2 + " is the ";

        for (int i = 0; i < generations - 2; i++) {
            relationship += "great ";
        }

        if (generations != 1) {
            relationship += "grand-";
        }
        relationship += directRelation + " of " + name1;

        System.out.println(relationship);

    }


    class Ancestor {
        private String name;
        private Ancestor mother;
        private Ancestor father;
        private Gender gender = Gender.UNKNOWN;

        int depth;

        public Ancestor(String name) {
            this.name = name;
        }

        public Ancestor(String name, Ancestor mother, Ancestor father) {
            this.mother = mother;
            this.father = father;
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Ancestor getMother() {
            return mother;
        }

        public void setMother(Ancestor mother) throws Exception {
            if (mother.gender.equals(Gender.MALE)) {
                invalidGenderError(mother.name);
                return;
            }
            this.mother = mother;
            mother.gender = Gender.FEMALE;
        }

        public Ancestor getFather() {
            return father;
        }

        public void setFather(Ancestor father) throws Exception {
            if (father.gender.equals(Gender.FEMALE)) {
                invalidGenderError(father.name);
                return;
            }
            this.father = father;
            father.gender = Gender.MALE;
        }

        private void invalidGenderError(String personsName) throws Exception {
            throw new Exception("The person " + personsName + " cannot be both male and female!");
        }

        public Gender getGender() {
            return gender;
        }

        public void setGender(Gender gender) {
            this.gender = gender;
        }

        public int getDepth() {
            return depth;
        }

        public void setDepth(int depth) {
            this.depth = depth;
        }

        public int hashCode() {
            return this.name.hashCode(); // each name should of an ancestor should be unique (as stated by the question)
        }
    }


}

