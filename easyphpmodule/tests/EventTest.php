<?php

class EventTest extends PHPUnit_Framework_TestCase
{
    public function testReserve()
    {
        $eventId = 1; 
        $eventName = '活動1';
        $eventStartDate = '2014-12-24 18:00:00';
        $eventEndDate = '2014-12-24 20:00:00';
        $eventDeadline = '2014-12-23 23:59:59';
        $eventAttendeeLimit = 10;
        $event = new \PHPUnitEventDemo\Event($eventId, $eventName, $eventStartDate, 
            $eventEndDate, $eventDeadline, $eventAttendeeLimit);
        $userId = 1;
        $userName = 'User1';
        $userEmail = 'user1@openfoundry.org';
        $user = new \PHPUnitEventDemo\User($userId, $userName, $userEmail);
        // 使用者報名活動
        $event->reserve($user);
        
        $expectedNumber = 1;
        // 預期報名人數
        $this->assertEquals($expectedNumber, $event->getAttendeeNumber());
        // 報名清單中有已經報名的人
        $this->assertContains($user, $event->attendees);
        return [$event, $user];
    }

    /**
     *  @depends testReserve
     */
    public function testUnreserve($objs)
    {
        // 測試使用者取消報名
        $event = $objs[0];
        $user = $objs[1];
        // 使用者取消報名
        $event->unreserve($user);
        $unreserveExpectedCount = 0;
        // 預期報名人數
        $this->assertEquals($unreserveExpectedCount, $event->getAttendeeNumber());
        // 報名清單中沒有已經取消報名的人
        $this->assertNotContains($user, $event->attendees);
    }
} 

?>
