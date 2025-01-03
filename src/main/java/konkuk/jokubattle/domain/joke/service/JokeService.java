package konkuk.jokubattle.domain.joke.service;

import konkuk.jokubattle.domain.joke.dto.request.JokeRequestDto;
import konkuk.jokubattle.domain.joke.dto.response.JokeResponseDto;
import konkuk.jokubattle.domain.joke.entity.Joke;
import konkuk.jokubattle.domain.joke.repository.JokeRepository;
import konkuk.jokubattle.domain.user.entity.User;
import konkuk.jokubattle.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class JokeService {
    private final JokeRepository jokeRepository;
    private final UserRepository userRepository;

    public JokeResponseDto createJoke(JokeRequestDto jokeRequestDto) {
        User user = userRepository.findById(jokeRequestDto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
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


    public List<JokeResponseDto> getAllJokes(int month, int week) {
        List<Joke> jokes = jokeRepository.findAllByMonthAndYear(month, week);

        WeekFields weekFields = WeekFields.of(Locale.getDefault());

        return jokes.stream()
                .filter(joke -> {
                    LocalDate jokeDate = joke.getCreatedAt().toLocalDate();
                    int jokeWeek = jokeDate.get(weekFields.weekOfMonth());
                    return jokeWeek == week;
                })
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



}
