package person;

import com.github.javafaker.Faker;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.ZoneId;
import java.util.Date;
import java.util.Locale;

public class Main {

    private static Faker faker = new Faker(new Locale("en"));

    private static Person randomPerson(){

        Date date = faker.date().birthday();

        Person person = Person.builder()
                .name(faker.name().name())
                .address(Address.builder()
                        .city(faker.address().city())
                        .country(faker.address().country())
                        .state(faker.address().state())
                        .streetAddress(faker.address().streetAddress())
                        .zip(faker.address().zipCode())
                        .build())
                .dob(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate())
                .email(faker.internet().emailAddress())
                .gender(faker.options().option(Person.Gender.class))
                .profession(faker.company().profession())
                .build();

        return person;
    }

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("person");
        EntityManager em = emf.createEntityManager();

        for(int i=0; i<147; i++){
            em.persist(randomPerson());
        }
        em.getTransaction().commit();

        em.close();
        emf.close();
    }
}
