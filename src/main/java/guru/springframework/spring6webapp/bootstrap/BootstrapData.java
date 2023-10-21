package guru.springframework.spring6webapp.bootstrap;

import guru.springframework.spring6webapp.domain.Author;
import guru.springframework.spring6webapp.domain.Book;
import guru.springframework.spring6webapp.domain.Publisher;
import guru.springframework.spring6webapp.repositories.AuthorRepository;
import guru.springframework.spring6webapp.repositories.BookRepository;
import guru.springframework.spring6webapp.repositories.PublisherRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class BootstrapData implements CommandLineRunner {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

    private final PublisherRepository publisherRepository;

    public BootstrapData(AuthorRepository authorRepository, BookRepository bookRepository, PublisherRepository publisherRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
        this.publisherRepository = publisherRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Author eric = new Author();
        eric.setFname("Eric");
        eric.setLname("Evans");

        Book ddd = new Book();
        ddd.setTitle("Domain Driven Design");
        ddd.setIsbn("123456");

        Publisher wiley = new Publisher("Wiley Books",
                                            "100 Main St, Clinton, NY",
                                            "New York",
                                            "NY",
                                            "10203"
                                        );



        Author ericSaved = authorRepository.save(eric);
        Book dddSaved = bookRepository.save(ddd);
        Publisher wileySaved = publisherRepository.save(wiley);


        // # 1 : associate book to author & other way
        ericSaved.getBooks().add(dddSaved);
        dddSaved.getAuthors().add(ericSaved);

        // save the association above
        authorRepository.save(ericSaved);

        // # 2 : associate publisher to book
        dddSaved.setPublisher(wileySaved);
        wileySaved.getBooks().add(dddSaved);

        // save book - publisher associate
        bookRepository.save(dddSaved);

        System.out.println("In Bootstrap");

        System.out.println("Author Count : " + authorRepository.count());
        System.out.println("Book Count : " + bookRepository.count());
        System.out.println("Publisher Count : " + publisherRepository.count());

    }
}
