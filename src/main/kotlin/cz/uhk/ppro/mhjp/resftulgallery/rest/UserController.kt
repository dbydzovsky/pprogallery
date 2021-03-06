package cz.uhk.ppro.mhjp.resftulgallery.rest

import cz.uhk.ppro.mhjp.resftulgallery.dto.NewUserDto
import cz.uhk.ppro.mhjp.resftulgallery.dto.RolesListDto
import cz.uhk.ppro.mhjp.resftulgallery.dto.UpdateUserDto
import cz.uhk.ppro.mhjp.resftulgallery.dto.UpdateUserPasswordDto
import cz.uhk.ppro.mhjp.resftulgallery.service.UserService
import cz.uhk.ppro.mhjp.resftulgallery.util.HeaderUtil
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest

@RestController
class UserController(
        private val userService: UserService,
        private val headerUtil: HeaderUtil
) {

    @PostMapping("/api/signup")
    fun createNewUser(@RequestBody newUserDto: NewUserDto) = userService.createEntity(newUserDto)

    @GetMapping("/api/u/{username}")
    fun retrieveUser(@PathVariable("username") username: String, request: HttpServletRequest)
            = userService.readEntity(username, headerUtil.getOptionalTokenFromHeader(request))

    @PutMapping("/api/u/{username}")
    fun updateUser(@PathVariable("username") username: String, @RequestBody updateUser: UpdateUserDto, request: HttpServletRequest)
            = userService.updateEntity(username, updateUser, headerUtil.getTokenFromHeader(request))

    @DeleteMapping("/api/u/{username}")
    fun deleteUser(@PathVariable("username") username: String, request: HttpServletRequest)
            = userService.deleteEntity(username, headerUtil.getTokenFromHeader(request))

    @PutMapping("/api/u/{username}/password")
    fun updatePassword(@PathVariable("username") username: String, @RequestBody newPasswordDto: UpdateUserPasswordDto, request: HttpServletRequest)
            = userService.changePassword(username, newPasswordDto, headerUtil.getTokenFromHeader(request))

    @PutMapping("/api/u/{username}/disable")
    fun disableUser(@PathVariable("username") username: String, request: HttpServletRequest)
            = userService.disableUser(username, headerUtil.getTokenFromHeader(request))

    @PutMapping("/api/u/{username}/enable")
    fun enableUser(@PathVariable("username") username: String, request: HttpServletRequest)
            = userService.enableUser(username, headerUtil.getTokenFromHeader(request))

    @GetMapping("/api/u/{username}/roles")
    fun getUserRoles(@PathVariable("username") username: String, request: HttpServletRequest)
            = userService.getUserRoles(username, headerUtil.getTokenFromHeader(request))

    @PutMapping("/api/u/{username}/roles")
    fun editUserRoles(@PathVariable("username") username: String, @RequestBody roles: RolesListDto, request: HttpServletRequest)
            = userService.updateUserRoles(username, headerUtil.getTokenFromHeader(request), roles)
}
