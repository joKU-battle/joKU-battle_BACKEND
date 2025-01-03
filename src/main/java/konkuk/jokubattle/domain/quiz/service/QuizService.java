package konkuk.jokubattle.domain.quiz.service;

import konkuk.jokubattle.domain.quiz.dto.QuizRequestDto;
import konkuk.jokubattle.domain.quiz.dto.response.QuizResponseDto;
import konkuk.jokubattle.domain.quiz.dto.request.QuizRecommendReqDto;
import konkuk.jokubattle.domain.quiz.dto.request.QuizSolveRequestDto;
import konkuk.jokubattle.domain.quiz.dto.QuizSolveResponseDto;
import konkuk.jokubattle.domain.quiz.dto.response.QuizRecommendResDto;
import konkuk.jokubattle.domain.quiz.entity.Quiz;
import konkuk.jokubattle.domain.quiz.repository.QuizRepository;
import konkuk.jokubattle.domain.user.entity.User;
import konkuk.jokubattle.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class QuizService {

    private final QuizRepository quizRepository;
    private final UserRepository userRepository;

    public QuizResponseDto createQuiz(QuizRequestDto requestDto) {
        User user = userRepository.findById(requestDto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        if(quizRepository.existsByQuestion(requestDto.getQuestion())) {
            throw new IllegalArgumentException("이미 존재하는 퀴즈입니다.");
        }
        Quiz quiz = Quiz.create(requestDto.getQuestion(), requestDto.getAnswer(), user);
        Quiz savedQuiz = quizRepository.save(quiz);

        return new QuizResponseDto(
                savedQuiz.getQuIdx(),
                savedQuiz.getQuestion(),
                savedQuiz.getCreatedAt().toString(),
                savedQuiz.getUser().getName(),
                savedQuiz.getUser().getDepartment(),
                savedQuiz.getRecommendation()
        );
    }

    public List<QuizResponseDto> getAllQuizzes() {
        LocalDateTime startOfToday = LocalDate.now().atStartOfDay();  // 오늘의 00:00:00
        LocalDateTime endOfToday = LocalDate.now().atTime(LocalTime.MAX);  // 오늘의 23:59:59

        List<Quiz> quizzes = quizRepository.findAllByTodayOrderByRecommendationDesc(startOfToday, endOfToday);

        return quizzes.stream()
                .map(quiz -> new QuizResponseDto(
                        quiz.getQuIdx(),
                        quiz.getQuestion(),
                        quiz.getCreatedAt().toString(),
                        quiz.getUser().getName(),
                        quiz.getUser().getDepartment(),
                        quiz.getRecommendation()
                ))
                .collect(Collectors.toList());
    }

    public Optional<QuizResponseDto> getQuizById(Long quizId) {
        return quizRepository.findById(quizId)
                .map(quiz -> new QuizResponseDto(
                        quiz.getQuIdx(),
                        quiz.getQuestion(),
                        quiz.getCreatedAt().toString(),
                        quiz.getUser().getName(),
                        quiz.getUser().getDepartment(),
                        quiz.getRecommendation()
                ));
    }

    public QuizSolveResponseDto solveQuiz(QuizSolveRequestDto requestDto) {
        Long quizId = requestDto.getQuizId();
        Optional<Quiz> quizOptional = quizRepository.findById(quizId);
        if (quizOptional.isPresent()) {
            Quiz quiz = quizOptional.get();
            if (quiz.getAnswer().equals(requestDto.getAnswer())) {
                quiz.setCorrect(quiz.getCorrect()+ 1);
                Quiz savedQuiz = quizRepository.save(quiz);
                return new QuizSolveResponseDto(savedQuiz.getQuIdx(), "정답입니다!");
            } else {
                quiz.setWrong(quiz.getWrong() + 1);
                Quiz savedQuiz = quizRepository.save(quiz);
                return new QuizSolveResponseDto(savedQuiz.getQuIdx(), "틀렸습니다!");
            }
        }
        return new QuizSolveResponseDto(quizId, "퀴즈를 찾을 수 없습니다.");
    }

    public QuizRecommendResDto increaseRecommendation(QuizRecommendReqDto requestDto){
        Long quizId = requestDto.getQuizId();
        Optional<Quiz> quizOptional = quizRepository.findById(quizId);
        if(quizOptional.isPresent()){
            Quiz quiz = quizOptional.get();
            quiz.setRecommendation(quiz.getRecommendation() + 1);
            Quiz savedQuiz = quizRepository.save(quiz);
            return new QuizRecommendResDto(savedQuiz.getQuIdx(),savedQuiz.getRecommendation());
        }
        throw new IllegalArgumentException("퀴즈를 찾을 수 없습니다.");
    }
}
