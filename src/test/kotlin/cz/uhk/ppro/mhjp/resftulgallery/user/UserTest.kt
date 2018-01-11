package cz.uhk.ppro.mhjp.resftulgallery.user

import com.nhaarman.mockito_kotlin.*
import cz.uhk.ppro.mhjp.resftulgallery.dao.RoleRepository
import cz.uhk.ppro.mhjp.resftulgallery.dao.UserRepository
import cz.uhk.ppro.mhjp.resftulgallery.domain.User
import cz.uhk.ppro.mhjp.resftulgallery.dto.NewUserDto
import cz.uhk.ppro.mhjp.resftulgallery.security.AuthorizationManager
import cz.uhk.ppro.mhjp.resftulgallery.security.PasswordValidator
import cz.uhk.ppro.mhjp.resftulgallery.service.UserServiceImpl
import cz.uhk.ppro.mhjp.resftulgallery.util.DtoValidation
import cz.uhk.ppro.mhjp.resftulgallery.util.HateoasUtil
import cz.uhk.ppro.mhjp.resftulgallery.util.PasswordsDontMatchException
import cz.uhk.ppro.mhjp.resftulgallery.util.UsernameAlreadyExistsException
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class UserTest {

    @Mock
    lateinit var userRepository: UserRepository

    @Mock
    lateinit var roleRepository: RoleRepository

    @Mock
    lateinit var authManager: AuthorizationManager

    @Mock
    lateinit var passwordValidator: PasswordValidator

    @Mock
    lateinit var dtoValidation: DtoValidation

    @Mock
    lateinit var hateoasUtil: HateoasUtil

    @InjectMocks
    lateinit var userService: UserServiceImpl

    @BeforeEach
    fun init() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun `Successful user creation`() {
        //given
        val newDto = NewUserDto("user", "pass", "pass")
        whenever(passwordValidator.hashPassword(any(), any())).thenReturn("hashed_pass")
        whenever(hateoasUtil.addSelfObjectLink(any())).thenReturn(mock())

        //when
        userService.createEntity(newDto)

        //then
        verify(dtoValidation, times(1)).validateDto(any())
        verify(userRepository, times(1)).getOneByUsername(any(), any<Class<Any>>())
        verify(passwordValidator, times(1)).hashPassword(any(), any())
        verify(userRepository, times(1)).save(any<User>())
        verify(hateoasUtil, times(1)).addSelfObjectLink(any())
    }

    @Test
    fun `Existing username return 409`() {
        //given
        val newDto = NewUserDto("user", "pass", "pass")
        given(userRepository.getOneByUsername(any(), any<Class<Any>>()))
                .willReturn(User(
                        username = "user",
                        name = "",
                        roles = emptyList(),
                        private = true,
                        enabled = true,
                        dateJoined = 0,
                        password = ""
                ))

        //then
        Assertions.assertThrows(
                UsernameAlreadyExistsException::class.java,
                {
                    //when
                    userService.createEntity(newDto)
                }
        )
        verify(dtoValidation, times(1)).validateDto(any())
        verify(userRepository, times(1)).getOneByUsername(any(), any<Class<Any>>())
    }

    @Test
    fun `Different passwords return 422`() {
        //given
        val newDto = NewUserDto("user", "pass", "ssap")

        //then
        Assertions.assertThrows(
                PasswordsDontMatchException::class.java,
                {
                    //when
                    userService.createEntity(newDto)
                }
        )
        verify(dtoValidation, times(1)).validateDto(any())
        verify(userRepository, times(1)).getOneByUsername(any(), any<Class<Any>>())
    }

}