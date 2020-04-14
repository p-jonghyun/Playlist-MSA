package user.Controller;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import user.Common.Paging.PageDto;
import user.Common.Paging.PageRequest;
import user.Common.Result.Result;
import user.Common.Result.ResultService;
import user.DTO.UserDto;
import user.Service.UserSearchService;
import user.Service.UserService;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserSearchService userSearchService;
    private final ResultService resultService;

    @GetMapping("")
    @ResponseStatus(value = HttpStatus.OK)
    public Result getUsers(PageRequest pageable) {
        Page<UserDto.UserRes> userpages = userSearchService.pageUsers(pageable.of("createdAt")).map(UserDto.UserRes::new);

        PageDto pages = new PageDto(userpages, "user");
        return resultService.getSuccessResult(new UserDto.PageUserRes(pages, userpages.getContent()));
    }

    @PostMapping("/signup")
    @ResponseStatus(value = HttpStatus.CREATED)
    public Result register(@RequestBody @Valid UserDto.SignupReq dto) {
        return resultService.getSuccessResult(new UserDto.SignupRes(userService.createUser(dto)));
    }

    @GetMapping("/{userId}")
    @ResponseStatus(value = HttpStatus.OK)
    public Result getUser(@PathVariable Long userId) {
        return resultService.getSuccessResult(new UserDto.UserRes(userService.getUser(userId)));
    }
}
