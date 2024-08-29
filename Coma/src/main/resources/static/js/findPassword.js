document.getElementById('resetForm').addEventListener('submit', async function(event) {
    event.preventDefault();

    const id = document.getElementById('id').value;
    const phoneNumber = document.getElementById('phoneNumber').value;
    const newPassword = document.getElementById('newPassword').value;
    const newPasswordConfirm = document.getElementById('newPasswordConfirm').value;

    if (!isVerified) {
        alert("전화번호 인증을 완료해주세요.");
        return;
    }

    if (newPassword !== newPasswordConfirm) {
        alert("비밀번호가 일치하지 않습니다.");
        return;
    }

    const response = await fetch('/api/users/reset-password', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            id: id,
            phoneNumber: phoneNumber,
            newPassword: newPassword
        })
    });

    const result = await response.text();

    if (response.ok) {
        alert("비밀번호가 성공적으로 재설정되었습니다.");
        window.location.href = '/users/login';
    } else {
        alert(result);
    }
});

let isVerified = false;

async function sendVerificationCode() {
    const id = document.getElementById('id').value; // 아이디 추가
    const phoneNumber = document.getElementById('phoneNumber').value;

    if (!phoneNumber.match(/^010\d{8}$/)) {
        alert("전화번호는 숫자 형태로만 입력해야 합니다.");
        return;
    }

    const response = await fetch(`/api/sms/find-password/send`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ id: id, phoneNumber: phoneNumber }) // 아이디와 전화번호 함께 전송
    });

    if (response.ok) {
        alert("인증번호가 전송되었습니다.");
        document.getElementById('verificationSection').style.display = 'block';
    } else {
        alert("인증번호 전송에 실패했습니다. 가입된 아이디와 전화번호를 확인해주세요.");
    }
}

async function verifyCode() {
    const phoneNumber = document.getElementById('phoneNumber').value;
    const verificationCode = document.getElementById('verificationCode').value;

    const response = await fetch(`/api/sms/verify`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            phoneNumber: phoneNumber,
            code: verificationCode
        })
    });

    if (response.ok) {
        alert("인증이 완료되었습니다.");
        isVerified = true;
        document.getElementById('resetPasswordSection').style.display = 'block';
        const verifyCodeConfirm = document.getElementById('verifyCodeConfirm');
        verifyCodeConfirm.disabled = true;
        verifyCodeConfirm.style.backgroundColor = 'gray';
        verifyCodeConfirm.style.cursor = 'not-allowed';

        document.getElementById('newPasswordSubmit').innerText = '비밀번호 재설정';

    } else {
        alert("인증에 실패했습니다. 인증번호를 다시 확인해주세요.");
        isVerified = false;
    }
}
