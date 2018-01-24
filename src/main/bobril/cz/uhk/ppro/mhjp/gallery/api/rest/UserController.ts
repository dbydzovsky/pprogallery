


// todo
// @RestController
// export interface UserController(
//         private userService: UserService,
//         private headerUtil: HeaderUtil
// ) {
//
//     @PostMapping("/api/signup")
//     fun createNewUser(@RequestBody newUserDto: NewUserDto) = userService.createEntity(newUserDto)
//
//     @GetMapping("/api/u/{username}")
//     fun retrieveUser(@PathVariable("username") username: string, request: HttpServletRequest)
//             = userService.readEntity(username, headerUtil.getOptionalTokenFromHeader(request))
//
//     @PutMapping("/api/u/{username}")
//     fun updateUser(@PathVariable("username") username: string, @RequestBody updateUser: UpdateUserDto, request: HttpServletRequest)
//             = userService.updateEntity(username, updateUser, headerUtil.getTokenFromHeader(request))
//
//     @DeleteMapping("/api/u/{username}")
//     fun deleteUser(@PathVariable("username") username: string, request: HttpServletRequest)
//             = userService.deleteEntity(username, headerUtil.getTokenFromHeader(request))
//
//     @PutMapping("/api/u/{username}/password")
//     fun updatePassword(@PathVariable("username") username: string, @RequestBody newPasswordDto: UpdateUserPasswordDto, request: HttpServletRequest)
//             = userService.changePassword(username, newPasswordDto, headerUtil.getTokenFromHeader(request))
//
//     @PutMapping("/api/u/{username}/disable")
//     fun disableUser(@PathVariable("username") username: string, request: HttpServletRequest)
//             = userService.disableUser(username, headerUtil.getTokenFromHeader(request))
//
//     @PutMapping("/api/u/{username}/enable")
//     fun enableUser(@PathVariable("username") username: string, request: HttpServletRequest)
//             = userService.enableUser(username, headerUtil.getTokenFromHeader(request))
//
//     @GetMapping("/api/u/{username}/roles")
//     fun getUserRoles(@PathVariable("username") username: string, request: HttpServletRequest)
//             = userService.getUserRoles(username, headerUtil.getTokenFromHeader(request))
//
//     @PutMapping("/api/u/{username}/roles")
//     fun editUserRoles(@PathVariable("username") username: string, @RequestBody roles: RolesListDto, request: HttpServletRequest)
//             = userService.updateUserRoles(username, headerUtil.getTokenFromHeader(request), roles)
// }
