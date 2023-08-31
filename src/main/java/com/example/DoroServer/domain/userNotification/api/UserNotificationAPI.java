package com.example.DoroServer.domain.userNotification.api;



import com.example.DoroServer.domain.user.entity.User;
import com.example.DoroServer.domain.userNotification.dto.UserNotificationRes;
import com.example.DoroServer.domain.userNotification.service.UserNotificationService;
import com.example.DoroServer.global.common.SuccessResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "알림📢")
@Slf4j
@RestController
@RequestMapping("/users-notifications")
@RequiredArgsConstructor
public class UserNotificationAPI {

    private final UserNotificationService userNotificationService;

     //모든 Notification 조회 메소드
    @ApiOperation(value = "유저의 전체 알림 조회", notes = "userId를 전달해서 해당 유저의 알림 전체를 조회합니다. 파라미터로 page랑 size 전달하시면 페이징 됩니다. 이게 Swagger가 잘 안돼서 Postman으로 테스트 해보시는게 나을거에요 Swagger는 이상하게 page랑 size를 인식못하네요")
    @GetMapping()
    public SuccessResponse findUserNotifications(@AuthenticationPrincipal User user,
            @PageableDefault(page = 0, size = 10, sort = "id", direction = Direction.DESC) Pageable pageable) {

        // 유저별 알림 조희
        List<UserNotificationRes> userNotifications = userNotificationService.findUserNotificationsByUserId(user.getId(),
                pageable);

        // 병합 후 조회된 전체 알림 페이징 후반환
        return SuccessResponse.successResponse(userNotifications);
    }

    @ApiOperation(value = "유저의 알림 조회", notes = "notificationId를 전달하면 현재 user 의 notification 을 전달합니다.")
    @GetMapping("/{notificationId}")
    public SuccessResponse findUserNotification(
            @AuthenticationPrincipal User user,
            @PathVariable("notificationId") Long notificationId) {

        UserNotificationRes userNotificationRes = userNotificationService.findNotificationById(
                user.getId(), notificationId);

        return SuccessResponse.successResponse(userNotificationRes);

    }

    @ApiOperation(value = "테스트 유저의 알림 삭제", notes = "userNotificationId 를 전달하면 해당 알림을 삭제합니다.")
    @DeleteMapping("/{notificationId}")
    public SuccessResponse deleteUserNotification(
            @PathVariable("notificationId") Long notificationId) {

        userNotificationService.deleteUserNotification(notificationId);

        return SuccessResponse.successResponse(notificationId+ "삭제");

    }


    @ApiOperation(value = "테스트 유저의 전체 알림 조회", notes = "userId를 전달해서 해당 유저의 알림 전체를 조회합니다. 파라미터로 page랑 size 전달하시면 페이징 됩니다. 이게 Swagger가 잘 안돼서 Postman으로 테스트 해보시는게 나을거에요 Swagger는 이상하게 page랑 size를 인식못하네요")
    @GetMapping("/test/{userId}")
    public SuccessResponse findUserNotificationsTest(@PathVariable("userId") Long userId,
            @PageableDefault(page = 0, size = 10, sort = "id", direction = Direction.DESC) Pageable pageable) {

        // 유저별 알림 조희
        List<UserNotificationRes> userNotifications = userNotificationService.findUserNotificationsByUserId(userId,
                pageable);

        // 병합 후 조회된 전체 알림 페이징 후반환
        return SuccessResponse.successResponse(userNotifications);
    }

    @ApiOperation(value = "테스트 유저의 알림 조회", notes = "notificationId를 전달하면 현재 user 의 notification 을 전달합니다.")
    @GetMapping("/test/{userId}/{notificationId}")
    public SuccessResponse findUserNotificationTest(
            @PathVariable("userId") Long userId,
            @PathVariable("notificationId") Long notificationId) {

        UserNotificationRes userNotificationRes = userNotificationService.findNotificationById(
                userId, notificationId);

        return SuccessResponse.successResponse(userNotificationRes);

    }



}
