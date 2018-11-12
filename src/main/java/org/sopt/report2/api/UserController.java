package org.sopt.report2.api;


import org.sopt.report2.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Controller
@RestController
public class UserController {

    private final static List<User> userList = new LinkedList<>();

    //현재시간 날짜 조회
    @GetMapping("")
    public String getTime() {
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String currentval = currentDateTime.format(formatter);
        return currentval + "입니다";
    }

    @GetMapping("/users")
    public String getUserList(
            @RequestParam(value = "name", required = false) final Optional<String> name,
            @RequestParam(value = "part", required = false) final Optional<String> part) {

        if (userList.isEmpty()) return "회원 리스트가 비었습니다.";

        if (name.isPresent()) {
            for (User user : userList) {
                if (user.getName().equals(name.get())) {
                    return user.toString();
                } else {
                    return "없습니다.";
                }
            }
        }
        if (part.isPresent()) {
            for (User user : userList) {
                if (user.getPart().equals(part.get())) {
                    return user.toString();
                } else {
                    return "없습니다.";
                }
            }
        }

        return null;
    }


    @GetMapping("/users/{userIdx}")
    public String getUserIdx(@PathVariable(value = "userIdx") final int userIdx) {
        for (User user : userList) {
            if (user.getUser_idx() == userIdx) {
                return user.toString();
            } else {
                return "없습니다.";
            }
        }
        return null;
    }

    @PostMapping("/users")
    public String addUser(@RequestBody final User user) {
        userList.add(user);
        return "저장 성공";
    }

    @PutMapping("/users/{userIdx}")
    public String UpdateUserInfo(
            @PathVariable(value = "userIdx") final int userIdx,
            @RequestBody final User user){
        for(User u: userList){
            if(u.getUser_idx() == userIdx){
                userList.remove(u);
                userList.add(user);
                return "수정 성공";
            }
        }
        return null;
    }


    @DeleteMapping("/users/{userIdx}")
    public String deleteUser(@PathVariable(value="userIdx") final  int userIdx){
        for(User user : userList){
            if(user.getUser_idx() == userIdx){
                userList.remove(user);
                return "삭제 성공";
            }
        }
        return "삭제 실패";
    }
}
