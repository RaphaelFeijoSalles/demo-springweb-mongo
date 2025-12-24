package com.raphalsalles.workshopmongo.services;

import com.raphalsalles.workshopmongo.domain.Post;
import com.raphalsalles.workshopmongo.repository.PostRepository;
import com.raphalsalles.workshopmongo.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    public Post findById(String id) {
        Optional<Post> obj = postRepository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Object not found"));

    }

    public List<Post> findByTitle(String text){
        return postRepository.searchTitle(text);
    }

    public List<Post> fullSearch(String text, Date minDate, Date maxDate) {

        // início do dia
                //pega a resposta e retorna para Date novamente
        minDate = Date.from(
                //converte para instant: representa um instante exato no tempo (UTC)
                minDate.toInstant()
                        //instante pertencente ao fuso horário do sistema
                        .atZone(ZoneId.systemDefault())
                        //descarta hora e fuso e fica só com o dia(transforma para localDate)
                        .toLocalDate()
                        //primeiro instante desse dia no fuso local
                        .atStartOfDay(ZoneId.systemDefault())
                        //converte de volta para instant
                        .toInstant()
        );

        // fim do dia
        maxDate = Date.from(
                maxDate.toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate()
                        //adiciona 1 dia
                        .plusDays(1)
                        //mas so vale o começo dele
                        .atStartOfDay(ZoneId.systemDefault())
                        .toInstant()
        );

        return postRepository.fullSearch(text, minDate, maxDate);
    }

}
