package crud_app;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Crud {
    public static volatile List<Person> allPeople = new ArrayList<Person>();

    static {
        allPeople.add(Person.createMale("Ron Hamnose", new Date()));  //test id=0
        allPeople.add(Person.createMale("Bob Bobington", new Date()));  //test id=1
    }

    public static void main(String[] args) {
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat outputFormat = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
            Date birthDate = null;
            if ("-c".equals(args[0])) {
                synchronized (allPeople) {
                    int countC = 0;
                    while (true) {
                        birthDate = inputFormat.parse(args[3 + countC * 3]);
                        if (args[2 + countC * 3].equals("m")) {
                            Person person = Person.createMale(args[1 + countC * 3], birthDate);
                            allPeople.add(person);
                            System.out.println(allPeople.indexOf(person));
                        } else if (args[2 + countC * 3].equals("w")) {
                            Person person = Person.createFemale(args[1 + countC * 3], birthDate);
                            allPeople.add(person);
                            System.out.println(allPeople.indexOf(person));
                        } else System.out.println("The gender is not correctly entered.");
                        countC++;
                    }
                }
            } else if ("-u".equals(args[0])) {
                synchronized (allPeople) {
                    int countU = 0;
                    while (true) {
                        birthDate = inputFormat.parse(args[4 + countU * 4]);
                        Person personUpdate = allPeople.get(Integer.parseInt(args[1 + countU * 4]));
                        personUpdate.setName(args[2 + countU * 4]);
                        personUpdate.setBirthDate(birthDate);
                        if (args[3 + countU * 4].equals("m")) {
                            personUpdate.setGender(Gender.MALE);
                        } else if (args[3 + countU * 4].equals("w")) {
                            personUpdate.setGender(Gender.FEMALE);
                        } else System.out.println("The gender is not correctly entered.");
                        countU++;
                    }
                }
            } else if ("-d".equals(args[0])) {
                synchronized (allPeople) {
                    int countD = 0;
                    while (true) {
                        Person personDelete = allPeople.get(Integer.parseInt(args[1 + countD]));
                        personDelete.setName(null);
                        personDelete.setBirthDate(null);
                        personDelete.setGender(null);
                        countD++;
                    }
                }
            } else if ("-i".equals(args[0])) {
                synchronized (allPeople) {
                    int countI = 0;
                    while (true) {
                        Person personInfo = allPeople.get(Integer.parseInt(args[1 + countI]));
                        if (personInfo.getName() != null) {
                            String info = personInfo.getName() + " ";
                            if (personInfo.getGender() == Gender.MALE)
                                info += "m ";
                            if (personInfo.getGender() == Gender.FEMALE)
                                info += "w ";
                            info += outputFormat.format(personInfo.getBirthDate());
                            System.out.println(info);
                            countI++;
                        }
                    }
                }
            }
        } catch (ParseException e) {
            e.getErrorOffset();
        }catch (ArrayIndexOutOfBoundsException e){
        }
    }
}
