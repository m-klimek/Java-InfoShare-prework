import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;
import java.io.File;

class Items{
    String name;
    double weight;

    public Items(String name, double weight) {
        this.name = name;
        this.weight = weight;
    }

    public String getName() {
        return name;
    }

    public double getWeight() {
        return weight;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

}

class Rocket implements SpaceShip{
    double rocketCost;
    double cargoCarried;
    double rocketWeight;
    double maxWeight;
    double maxCargoWeight = maxWeight - rocketWeight;
    public Rocket(){
        double rocketCost;
        double cargoCarried;
        double rocketWeight;
        double maxWeight;
        double maxCargoWeight = this.maxWeight - this.rocketWeight;
    }

    public boolean launch(){
        return true;
    }

    public boolean land(){
        return true;
    }


    public double carry(Items item){
        this.cargoCarried += item.weight;
        return cargoCarried;
    }

    public final double getCargoCarried(){
        return cargoCarried;
    }

    public boolean canCarry(Items item){

        if((this.cargoCarried+item.weight>this.maxCargoWeight)){
//            System.out.println("Can't carry more cargo!!!");
            return false;
        }
        else{
//            System.out.println("Cargo added!!!");
            return true;
        }
    }

}

class U1 extends Rocket{

    public U1() {
        this.cargoCarried = 0;
        this.rocketCost = 100000000;
        this.rocketWeight = 10000;
        this.maxWeight = 18000;
        this.maxCargoWeight = this.maxWeight - this.rocketWeight;
    }

    public boolean launch(){
        double explosionChance = 0.05*(getCargoCarried()/maxCargoWeight);
        double rNumber = Math.random()/3;
        if(rNumber<explosionChance){
//            System.out.println("U1 Rocket exploded!");
            return false;
        }else{
//            System.out.println("U1 Rocket launched succesfully!");
            return true;
        }
    }

    public boolean land(){
        double crashChance = 0.01*(getCargoCarried()/maxCargoWeight);
        double rNumber = Math.random()/3;
        if(rNumber<crashChance){
//            System.out.println("U1 Rocket crashed on landing!");
            return false;
        }else{
//            System.out.println("U1 Rocket landed succesfully!");
            return true;
        }
    }

}

class U2 extends Rocket{
    U2(){
        this.cargoCarried = 0;
        this.rocketCost = 120000000;
        this.rocketWeight = 18000;
        this.maxWeight = 29000;
        this.maxCargoWeight = this.maxWeight - this.rocketWeight;
    }

    public boolean launch(){
        double explosionChance = 0.04*(getCargoCarried()/maxCargoWeight);
        double rNumber = Math.random()/3;
        if(rNumber<explosionChance){
//            System.out.println("U2 Rocket exploded!");
            return false;
        }else{
//            System.out.println("U2 Rocket launched succesfully!");
            return true;
        }
    }

    public boolean land(){
        double crashChance = 0.08*(getCargoCarried()/maxCargoWeight);
        double rNumber = Math.random()/3;
        if(rNumber<crashChance){
//            System.out.println("U2 Rocket crashed on landing!");
            return false;
        }else{
//            System.out.println("U2 Rocket landed succesfully!");
            return true;
        }
    }
}

class Simulation{

    ArrayList loadItems(String fileName) throws Exception{
        File file = new File(fileName);
        Scanner fileRead = new Scanner(file);
        ArrayList itemArray = new ArrayList();
        while(fileRead.hasNextLine()){
            fileRead.useDelimiter("=");
            String itemName = fileRead.next();
//            System.out.println("Itemek: " + itemName);
            fileRead.skip("=");
            int itemWeight = Integer.valueOf(fileRead.nextLine());
//            System.out.println("Waga: " + itemWeight);
            Items item = new Items(itemName, itemWeight);
            itemArray.add(item);
        }
        return itemArray;
    }

    ArrayList loadU1(ArrayList array){
        ArrayList arrayU1 = new ArrayList();
        while(!(array.isEmpty())){
            int size = array.size();
            Rocket rocketU1 = new U1();
            for(int i = (size-1);i>=0;i--){
                Object itemTemp = array.get(i);
                if(rocketU1.canCarry((Items) itemTemp)){
                    rocketU1.carry((Items) itemTemp);
                    array.remove(i);
                } else{
                    break;
                }
            }
            arrayU1.add(rocketU1);
        }
        return arrayU1;
    }

    ArrayList loadU2(ArrayList array2){
        ArrayList arrayU2 = new ArrayList();
        while(!(array2.isEmpty())){
            int size = array2.size();
            Rocket rocketU2 = new U2();
            for(int i = (size-1);i>=0;i--){
                Object itemTemp = array2.get(i);
                if(rocketU2.canCarry((Items) itemTemp)){
                    rocketU2.carry((Items) itemTemp);
                    array2.remove(i);
                } else{
                    break;
                }
            }
            arrayU2.add(rocketU2);
        }
        return arrayU2;
    }

    double runSimulation(ArrayList rocketList){
        double totalBudget = 0;
        while(!(rocketList.isEmpty())){
            for(int i=(rocketList.size()-1);i>=0;i--){
                Rocket rocket = (Rocket) rocketList.get(i);
                while(true){
                    totalBudget += rocket.rocketCost;
                    boolean launching = rocket.launch();
                    boolean landing = rocket.land();
                    if(launching == true && landing == true){
                        break;
                    }
                }
                rocketList.remove(i);
            }
        }
        return (totalBudget/1000000);
    }
}

public class SpaceChallenge {
    public static void main(String[] args) throws Exception {
        Simulation obj = new Simulation();
        ArrayList<Items> phase1ItemsU1 = obj.loadItems("phase-1.txt");
        ArrayList<Items> phase2ItemsU1 = obj.loadItems("phase-2.txt");
        ArrayList<Items> phase1ItemsU2 = obj.loadItems("phase-1.txt");
        ArrayList<Items> phase2ItemsU2 = obj.loadItems("phase-2.txt");

        ArrayList<U1> rocketsU1phase1 = obj.loadU1(phase1ItemsU1);
        ArrayList<U1> rocketsU1phase2 = obj.loadU1(phase2ItemsU1);
        ArrayList<U1> rocketsU2phase1 = obj.loadU2(phase1ItemsU2);
        ArrayList<U1> rocketsU2phase2 = obj.loadU2(phase2ItemsU2);

        double budgetU1phase1 = obj.runSimulation(rocketsU1phase1);
        double budgetU1phase2 = obj.runSimulation(rocketsU1phase2);
        double budgetU2phase1 = obj.runSimulation(rocketsU2phase1);
        double budgetU2phase2 = obj.runSimulation(rocketsU2phase2);

        System.out.println("Budżet potrzebny na wykonanie fazy 1 misji na Marsa dla Rakiet U1 wynosi: " + String.format("%.0f", budgetU1phase1) + " milionów $");
        System.out.println("Budżet potrzebny na wykonanie fazy 2 misji na Marsa dla Rakiet U1 wynosi: " + String.format("%.0f", budgetU1phase2) + " milionów $");
        System.out.println("Budżet potrzebny na wykonanie fazy 1 misji na Marsa dla Rakiet U2 wynosi: " + String.format("%.0f", budgetU2phase1) + " milionów $");
        System.out.println("Budżet potrzebny na wykonanie fazy 2 misji na Marsa dla Rakiet U2 wynosi: " + String.format("%.0f", budgetU2phase2) + " milionów $");

        double totalBudgetU1 = budgetU1phase1 + budgetU1phase2;
        double totalBudgetU2 = budgetU2phase1 + budgetU2phase2;

        System.out.println("\nŁączny budżet misji na Marsa potrzebny na wysłanie wszystkich rakiet U1 wynosi: " +  String.format("%.0f", totalBudgetU1) + " milionów $");
        System.out.println("Łączny budżet misji na Marsa potrzebny na wysłanie wszystkich rakiet U2 wynosi: " +  String.format("%.0f",totalBudgetU2) + " milionów $");
    }
}
