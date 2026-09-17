package org.example.springtestci.app;

import org.example.springtestci.domain.Flower;
import org.example.springtestci.domain.FlowerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@DisplayName("FlowerService 실패 유도용 단위 테스트")
class FlowerServiceFailingTest {

    private final FlowerRepository flowerRepository = mock(FlowerRepository.class);

    private FlowerService flowerService;

    @BeforeEach
    void setUp() {
        // 목(mock) 저장소를 주입해 서비스 생성
        flowerService = new FlowerService(flowerRepository);
    }

    @Test
    @DisplayName("count 호출 결과가 저장소 값과 다르면 실패해야 한다")
    void count_intentionallyFails() {
        // given: 저장소가 개수 3을 반환하도록 설정
        given(flowerRepository.count()).willReturn(3L);

        // when: 서비스의 count 메서드를 호출
        long count = flowerService.count();

        // then: CI 실패 확인을 위해 의도적으로 잘못된 기대값 사용
        assertThat(count).isEqualTo(999L);
    }
}
