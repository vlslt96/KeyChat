// var openWin = window.open("./chatroom.jsp",
//     "childForm", "width=570, height=350, resizable = yes, scrollbars = yes");
// }

function getMyChannel() {
    $.ajax({
        type: 'POST',
        url: '/channel/me',
        success: function (response) {
            var table = document.getElementById('channel_table');
            var channelList = "";
            $.each(response.result, function (index, value) {
                table.innerHTML += "<tr class='tr1'>"
                    + " " + "<td class='num' rowspan='2'> 1 </td>"
                    + " " + "<td class='channel-title'>" + value.name + "</td>"
                    + " " + "</tr>"
                    + " " + "<tr class='tr2'>"
                    + " " + "<td class='channel-leader'>" + value.leader + "</td>"
                    + " " + "</tr>";

                // channelList += "<table style='width: 100%' border='1'>";
                // channelList += "<tr class='tr1'>";
                // channelList += "<td class='num' rowspan='2'> 1 </td>";
                // channelList += "<td class='channel-title'>" + value.name;+" </td>";
                // channelList += "</tr>";
                // channelList += "<tr class='tr2'>";
                // channelList += "<td class='channel-leader'>" + value.leader + "</td>";
                // channelList += "</tr>";
                // channelList += "</table>";
            });

            $(".conversation-wrap").append(channelList);

            alert("내 채널 불러오기 성공");
        },
        error: function (response) {
            alert("내 채널 불러오기 실패");
        }
    });
}

function createChannel() {
    var reqJson = {
        requestMsg: {
            name: $("#createChannelName").val(),
            password: $("#createChannelPassword").val(),
            limitCapacity: $("#createChannelLmitCapacity").val(),
            limitTime: $("#createChannelLmitTime").val(),
            limitAnonym: $("#createChannelLmitAnonym").val(),
            hashtags: $("#createChannelLmitHashtag").val().split(", ")
        }
    };

    console.log(reqJson);

    $.ajax({
        type: 'POST',
        url: '/channel/create',
        data: JSON.stringify(reqJson),
        async: false,
        contentType: 'application/json; charset=utf-8',
        success: function (response) {
            alert("채널 생성 성공");
            location.reload();
            // setUserNickname();
        },
        error: function (response) {
            console.log(response);
            alert("로그인 실패");
        }
    });
}

function getChannels() {
    $.ajax({
        type: 'POST',
        url: '/channel/list',
        contentType: 'application/json; charset=utf-8',
        success: function (response) {
            var channelsListRow = "";
            $.each(response.result, function (index, value) {
                channelsListRow += "<tr>\n" +
                    "            <td>" + value.name + "</td>\n" +
                    "            <td>" + value.leader + "</td>\n" +
                    "            <td>" + value.limitCapacity + "</td>\n" +
                    "            <td>" + value.limitTime + "</td>\n" +
                    "            <td>" + value.limitAnonym + "</td>\n" +
                    "            <td>" + value.createdDatetime + "</td>\n" +
                    "            <a href=\"#\" class=\"view\" title=\"View\" data-toggle=\"tooltip\"><i class=\"material-icons\">&#xE417;</i></a>\n" +
                    "            <a href=\"#\" class=\"edit\" title=\"Edit\" data-toggle=\"tooltip\"><i class=\"material-icons\">&#xE254;</i></a>\n" +
                    "            <a href=\"#\" class=\"delete\" title=\"Delete\" data-toggle=\"tooltip\"><i class=\"material-icons\">&#xE872;</i></a>\n" +
                    "            </td>\n" +
                    "            </tr>";   ;
            });
            $("#channelsListRow").append(channelsListRow);
            alert("채널 리스트 성공")
        },
        error: function (response) {
            alert("채널 리스트 실패");
        }
    });
}