package cz.uhk.ppro.mhjp.resftulgallery.rest

import cz.uhk.ppro.mhjp.resftulgallery.dto.NewUserDto
import cz.uhk.ppro.mhjp.resftulgallery.dto.SubmitReportDto
import cz.uhk.ppro.mhjp.resftulgallery.dto.UpdateUserDto
import cz.uhk.ppro.mhjp.resftulgallery.dto.UpdateUserPasswordDto
import cz.uhk.ppro.mhjp.resftulgallery.service.UserService
import cz.uhk.ppro.mhjp.resftulgallery.util.getOptionalTokenFromHeader
import cz.uhk.ppro.mhjp.resftulgallery.util.getTokenFromHeader
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest

@RestController
class UserController(private val userService: UserService) {

    @PostMapping("/signup")
    fun createNewUser(@RequestBody newUserDto: NewUserDto) = userService.createEntity(newUserDto)

    @GetMapping("/u/{username}")
    fun retrieveUser(@PathVariable("username") username: String, request: HttpServletRequest)
            = userService.readEntity(username, getOptionalTokenFromHeader(request))

    @PutMapping("/u/{username}")
    fun updateUser(@PathVariable("username") username: String, @RequestBody updateUser: UpdateUserDto, request: HttpServletRequest)
            = userService.updateEntity(username, updateUser, getTokenFromHeader(request))

    @DeleteMapping("/u/{username}")
    fun deleteUser(@PathVariable("username") username: String, request: HttpServletRequest)
            = userService.deleteEntity(username, getTokenFromHeader(request))

    @PutMapping("/u/{username}/password")
    fun updatePassword(@PathVariable("username") username: String, @RequestBody newPasswordDto: UpdateUserPasswordDto, request: HttpServletRequest)
            = userService.changePassword(username, newPasswordDto, getTokenFromHeader(request))

    @PutMapping("/u/{username}/disable")
    fun disableUser(@PathVariable("username") username: String, request: HttpServletRequest)
            = userService.disableUser(username, getTokenFromHeader(request))

    @PutMapping("/u/{username}/enable")
    fun enableUser(@PathVariable("username") username: String, request: HttpServletRequest)
            = userService.enableUser(username, getTokenFromHeader(request))

    @PostMapping("/u/{username}/report")
    fun reportUser(@PathVariable("username") username: String, @RequestBody report: SubmitReportDto, request: HttpServletRequest)
            = userService.reportUser(username, report, getTokenFromHeader(request))

    @GetMapping("/u/{username}/roles")
    fun getUserRoles(@PathVariable username: String, request: HttpServletRequest)
            = userService.getUserRoles(username, getTokenFromHeader(request))
}
