import java.util.ArrayList;
import java.util.List;

class Load {        //Груз
    private String contentName;    //Наименование содержимого
    private int weight;            //Вес содержимого

    public Load(String newContentName, int newWeight) {    //Инициализация груза (конструктор)
        contentName = newContentName;
        weight = newWeight;
    }

    public Load(String newContentName) {
        contentName = newContentName;
        weight = 0;
    }

    public Load() {
        contentName = "none";
        weight = 0;
    }

    public int GetWeight() {
        return weight;
    }

    public void Output() {    //Вывод инф. о грузе
        System.out.printf("Тип содержимого: %s\n", contentName);
        System.out.printf("Вес содержимого (в одном к-ре): %d\n", weight);
    }
}

class Container {    //Контейнер
    private String destinationAddress;    //Адрес назначения
    private String IDNumber;              //Идентификационный номер
    private Load load;                    //Груз в контейнере

    public Container(String newDestinationAddress, String newIDNumber, Load newLoad) {
        destinationAddress = newDestinationAddress;
        IDNumber = newIDNumber;
        load = newLoad;
    }

    public Container(String newIDNumber) {
        destinationAddress = "none";
        IDNumber = newIDNumber;
    }

    public Container() {
        destinationAddress = "none";
        IDNumber = "none";
    }

    public Load GetLoad() {
        return load;
    }

    public void CorrectContainerID(String newIDNumber) {    //Скорректировать ID контейнера
        IDNumber = newIDNumber;
    }

    public void Output() {    //Вывод инф. о контейнере
        System.out.printf("Адрес назначения: %s\n", destinationAddress);
        System.out.printf("Идентификационный номер к-ра: %s\n", IDNumber);
        load.Output();
    }
}

class Team {     //Команда
    private int numberOfPeople;           //Кол-во членов команды
    private String foremanName;           //Имя бригадира

    public Team(int newNumberOfPeople, String newForemanName) {    //Инициализация команды (конструктор)
        numberOfPeople = newNumberOfPeople;
        foremanName = newForemanName;
    }

    public Team(String newForemanName) {
        numberOfPeople = 0;
        foremanName = newForemanName;
    }

    public Team() {
        numberOfPeople = 0;
        foremanName = "none";
    }

    public int GetNumberOfPeople() {
        return numberOfPeople;
    }

    public void Output() {    //Вывод инф. о команде
        System.out.printf("Кол-во членов команды: %d\n", numberOfPeople);
        System.out.printf("Имя бригадира: %s\n", foremanName);
    }
}

class Captain {     //Капитан
    private String name;             //Имя
    private String licenseNumber;    //№ лицензии
    private Team team;               //Команда капитана

    public Captain(String newName, String newLicenseNumber, Team newTeam) {    //Инициализация капитана (конструктор)
        name = newName;
        licenseNumber = newLicenseNumber;
        team = newTeam;
    }

    public Captain(String newName) {
        name = newName;
        licenseNumber = "none";
    }

    public Captain() {
        name = "none";
        licenseNumber = "none";
    }

    public Team GetTeam() {
        return team;
    }

    public String GetLicense() {
        return licenseNumber;
    }

    public void Output() {    //Вывод инф. о капитане
        System.out.printf("Имя капитана: %s\n", name);
        System.out.printf("№ лицензии капитана: %s\n", licenseNumber);
        team.Output();
    }
}

class CaptainDatabase {       //База доступных капитанов (вспомог. класс)
    private List<Captain> Database;
    private int arrayCounter;

    public CaptainDatabase() {
        Database = new ArrayList<>();
        arrayCounter = 0;
    }

    public void AddEntry(Captain newCaptain) {
        Database.add(newCaptain);
        arrayCounter++;
    }

    public Captain GetCaptain(String licenseNumber) {
        for (int i = 0; i < arrayCounter; i++) {
            if (Database.get(i).GetLicense().equals(licenseNumber)) {
                return Database.get(i);
            }
        }
        return null;
    }
}

class Ship {               //Корабль
    private Container container;         //Тип контейнеров, которые перевозит корабль
    private int numberOfContainers;      //Кол-во контейнеров на корабле
    private Captain captain;             //Капитан корабля
    private boolean condition;           //Состояние корабля (приемлемое/нет)

    public Ship(Container newContainer, int newNumberOfContainers, String newAssignedCaptainLicense, boolean newCondition, CaptainDatabase Database1) {    //Инициализация корабля (конструктор)
        container = newContainer;
        numberOfContainers = newNumberOfContainers;
        captain = Database1.GetCaptain(newAssignedCaptainLicense);
        condition = newCondition;
    }

    public Ship(String newAssignedCaptainLicense, CaptainDatabase Database1) {
        captain = Database1.GetCaptain(newAssignedCaptainLicense);
        numberOfContainers = 0;
        condition = false;
        captain = null;
    }

    public Ship() {
        numberOfContainers = 0;
        condition = false;
        captain = null;
    }
    
    public void AddLoad(int additionalLoad) {    //Добавить груз
        numberOfContainers += additionalLoad;
    }

    public void ChangeShipContainers(Container newContainer, int newNumberOfContainers) {    //Перезагрузить корабль (другими контейнерами)
        container = newContainer;
        numberOfContainers = newNumberOfContainers;
    }
	
    public void ChangeShipContainers(int newNumberOfContainers) {                            //Перезагрузить корабль (только кол-во)
        numberOfContainers = newNumberOfContainers;
    }

    public boolean CheckViolations() {     //Проверить нарушения
        if (!condition || captain.GetTeam().GetNumberOfPeople() > 20 || (numberOfContainers * container.GetLoad().GetWeight() > 500)) {
            return true;
        } else {
            return false;
        }
    }

    public int CalcFee() {       //Рассчитать плату за проход
        int fine = 0;
        if (CheckViolations()) {
            fine = 5000;
        }
        return (numberOfContainers * container.GetLoad().GetWeight() + fine);
    }

    public void Output() {        //Вывод всей инф. о корабле
        container.Output();
        System.out.printf("Кол-во контейнеров: %d\n", numberOfContainers);
        captain.Output();
        if (condition) {
            System.out.println("Состояние приемлимое.");
        } else {
            System.out.println("Состояние неприемлимое.");
        }
    }
}



public class HelloWorld {
	public static void main(String[] args) {
        CaptainDatabase globalDatabase1 = new CaptainDatabase();
        
        Load fish = new Load("Fish", 50);
        Load furniture = new Load("Furniture", 100);
        Container contOne = new Container("-15 20 150", "1234567", fish);
        Container contTwo = new Container("-150 70 -10", "7654321", furniture);
        Team teamOne = new Team(10, "Joe");
        Team teamTwo = new Team(5, "Mark");
        Captain captainOne = new Captain("Josh", "1234567", teamOne);
        Captain captainTwo = new Captain("Jon", "7654321", teamTwo);
        globalDatabase1.AddEntry(captainOne);
        globalDatabase1.AddEntry(captainTwo);
        
        Ship shipOne = new Ship(contOne, 10, "1234567", true, globalDatabase1);
        System.out.printf("Плата за проход 1: %d\n", shipOne.CalcFee());
        
        shipOne.ChangeShipContainers(contTwo, 20);
        System.out.printf("Плата за проход 2: %d\n", shipOne.CalcFee());
        
        shipOne.AddLoad(10);
        System.out.printf("Плата за проход 3: %d\n", shipOne.CalcFee());
        
        Ship shipTwo = new Ship(contTwo, 10, "7654321", false, globalDatabase1);
        System.out.printf("Плата за проход 4: %d\n\n", shipTwo.CalcFee());
        
        shipTwo.Output();
        
        Ship[] shipArray = new Ship[]{new Ship(contOne, 10, "1234567", true, globalDatabase1), shipTwo, new Ship(contOne, 20, "1234567", true, globalDatabase1)};
        System.out.printf("\nПлата за проход 5: %d\n\n", shipArray[2].CalcFee());
        
        shipArray[0].Output();
        
        System.out.println("Завершение работы.");
    }
}
