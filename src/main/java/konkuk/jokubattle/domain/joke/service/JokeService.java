package konkuk.jokubattle.domain.joke.service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import konkuk.jokubattle.domain.joke.dto.request.JokeRequestDto;
import konkuk.jokubattle.domain.joke.dto.response.JokeResponseDto;
import konkuk.jokubattle.domain.joke.dto.response.JokeWorldCupRes;
import konkuk.jokubattle.domain.joke.entity.Joke;
import konkuk.jokubattle.domain.joke.repository.JokeRepository;
import konkuk.jokubattle.domain.user.entity.User;
import konkuk.jokubattle.domain.user.repository.UserRepository;
import konkuk.jokubattle.global.exception.CustomException;
import konkuk.jokubattle.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Transactional
public class JokeService {
    private final JokeRepository jokeRepository;
    private final UserRepository userRepository;

    public JokeResponseDto createJoke(Long usIdx, JokeRequestDto jokeRequestDto) {
        User user = userRepository.findById(usIdx)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        Joke joke = Joke.create(jokeRequestDto.getContent(), user);
        Joke savedJoke = jokeRepository.save(joke);

        return new JokeResponseDto(
                savedJoke.getJoIdx(),
                savedJoke.getContent(),
                savedJoke.getCreatedAt().toString(),
                savedJoke.getUser().getName(),
                savedJoke.getUser().getDepartment(),
                savedJoke.getPickedCount()
        );
    }


    public List<JokeResponseDto> getAllJokes() {
        return jokeRepository.findAll().stream()
                .map(joke -> new JokeResponseDto(
                        joke.getJoIdx(),
                        joke.getContent(),
                        joke.getCreatedAt().toString(),
                        joke.getUser().getName(),
                        joke.getUser().getDepartment(),
                        joke.getPickedCount()
                ))
                .collect(Collectors.toList());
    }

    public List<JokeWorldCupRes> jokeWorldCup() {
        List<Joke> random8Data = jokeRepository.findRandom8Data();
        return random8Data.stream()
                .map(joke -> new JokeWorldCupRes(joke.getJoIdx(), joke.getContent()))
                .toList();
    }

    public Boolean worldCupSelect(Long joIdx) {
        Joke joke = jokeRepository.findById(joIdx)
                .orElseThrow(() -> new CustomException(ErrorCode.JOKE_NOT_FOUND));
        joke.select();
        return Boolean.TRUE;
    }
}
