package <%= package_name %>.domain.usecase

import <%= package_name %>.UnitTest
import <%= package_name %>.domain.TestSchedulerProvider
import <%= package_name %>.domain.repository.HeaderRepository
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito

class GetHeadersUseCaseTest : UnitTest() {
    private lateinit var getHeadersUseCase: GetHeadersUseCase
    @Mock
    private lateinit var headerRepository: HeaderRepository
    @Mock
    private lateinit var testSchedulerProvider: TestSchedulerProvider

    @Before
    @Throws(Exception::class)
    fun setup() {
        getHeadersUseCase = GetHeadersUseCase(testSchedulerProvider, headerRepository)
    }

    @Test
    fun testGetHeadersSuccess() {
        getHeadersUseCase.buildUseCaseFlowable(null)
        Mockito.verify<HeaderRepository>(headerRepository).headers()
        Mockito.verifyNoMoreInteractions(headerRepository)
        Mockito.verifyZeroInteractions(testSchedulerProvider)
    }
}