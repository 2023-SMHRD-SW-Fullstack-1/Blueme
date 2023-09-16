/*
작성자: 이유영
날짜(수정포함): 2023-09-15
설명: 회원관련 리덕스 타입/ 액션 정의
*/
//로그인 타입 정의
export const LOGIN_REQUEST = 'LOGIN_REQUEST'; //로그인 요청
export const LOGIN_SUCCESS = 'LOGIN_SUCCESS'; //로그인 성공
export const LOGIN_FAILURE = 'LOGIN_FAILURE'; //로그인 실패
export const LOGOUT_SUCCESS = 'LOGOUT_SUCCESS'; //로그아웃
export const USER_DELETE = 'USER_DELETE'; //회원탈퇴
export const USER_UPDATE = 'USER_UPDATE' //회원 정보 수정

//로그인 요청 시
export function loginRequest() {
    return {
        type: LOGIN_REQUEST
    }
}
//로그인 성공 시
export function loginSuccess(user) {
    return {
        type: LOGIN_SUCCESS, 
        payload: user
    }
}
//로그인 실패 시
export function loginFailure(error) {
    return {
        type: LOGIN_FAILURE, 
        payload: error 
    }
}
//로그아웃
export function logoutSuccess() {
    return {
        type: LOGOUT_SUCCESS,
    }
}
//회원탈퇴
export function userDelete() {
    return {
        type: USER_DELETE,
    }
}
//회원 정보 수정 시
export function userUpdate(user) {
    return {
        type: USER_UPDATE, 
        payload: user
    }
}
