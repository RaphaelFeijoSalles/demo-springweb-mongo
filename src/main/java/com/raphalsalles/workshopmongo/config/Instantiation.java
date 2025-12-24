package com.raphalsalles.workshopmongo.config;

import com.raphalsalles.workshopmongo.domain.Post;
import com.raphalsalles.workshopmongo.domain.User;
import com.raphalsalles.workshopmongo.dto.AuthorDTO;
import com.raphalsalles.workshopmongo.dto.CommentDTO;
import com.raphalsalles.workshopmongo.repository.PostRepository;
import com.raphalsalles.workshopmongo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.TimeZone;

@Configuration
public class Instantiation implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Override
    public void run(String... args) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));

        userRepository.deleteAll();
        postRepository.deleteAll();

        User maria = new User(null, "Maria Brown", "maria@gmail.com");
        User alex = new User(null, "Alex Green", "alex@gmail.com");
        User bob = new User(null, "Bob Grey", "bob@gmail.com");

        //É preciso persistir os Users antes de associar com algo
        //Evita id null
        userRepository.saveAll(Arrays.asList(maria, alex, bob));

        Post post1 = new Post(null, sdf.parse("2025/12/22"), "Let's travel!", "I'm going to travel to São Paulo. Hugs!", new AuthorDTO(maria));
        Post post2 = new Post(null, sdf.parse("2025/12/20"), "Good morning", "I woke up happy!", new AuthorDTO(maria));

        CommentDTO c1 = new CommentDTO("Good travel man!", sdf.parse("2025/12/24"), new AuthorDTO(alex));
        CommentDTO c2 = new CommentDTO("Enjoy it!", sdf.parse("2025/12/22"), new AuthorDTO(bob));
        CommentDTO c3 = new CommentDTO("Have a nice day!", sdf.parse("2025/12/23"), new AuthorDTO(alex));

        post1.getComments().addAll(Arrays.asList(c1, c2));
        post2.getComments().add(c3);


        postRepository.saveAll(Arrays.asList(post1, post2));

        maria.getPosts().addAll(Arrays.asList(post1, post2));

        userRepository.save(maria);

    }
}
