// 현재 logout.js 파일 작동안함 = 각페이지 별로 하드코딩 (이후 수정해야 함)
/*function performLogout() {
    fetch('/api/users/logout', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        }
    })
        .then(response => {
            if (response.ok) {
                // 현재 페이지로 리다이렉트
                window.location.href = window.location.href;
            } else {
                alert('로그아웃에 실패했습니다.');
            }
        })
        .catch(error => console.error('Error:', error));
}

console.log("Logout script loaded");
*/