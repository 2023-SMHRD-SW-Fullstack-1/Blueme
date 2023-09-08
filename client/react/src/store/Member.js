// 리덕스 하는 중


// // 액션 타입 지정하기
// const MEMBER_INSERT = 'MEMBER_INSERT';
// const MEMBER_UPDATE = 'MEMBER_UPDATE';
// const MEMBER_REMOVE = 'MEMBER_REMOVE';
 
// // 회원 추가
// export const memberInsert = (id, email, nick, pw) => (
//     {
//     type: MEMBER_INSERT,
//     //payload를 작성해주면 type뿐만 아니라 payload의 key,value를 같이 담아서 보내줌
//     payload: { id: id, email: email, nick: nick, pw: pw },
//     }
// )
// // 회원 정보 수정
// export const memberUpdate = (id, email, nick, pw) => (
//     {
//         type: MEMBER_UPDATE,
//         payload: { id: id, email: email, nick: nick, pw: pw},
//     }
// )
// // 회원 탈퇴
// export const memberRemove = (id, email) => (
//     {
//         type: MEMBER_REMOVE,
//         payload: { id: id, email: email }
//     }
// )
 
// // 초기화(초기값 설정)
// const initialState = {
//     email: '',
//     nick: '',
//     pw: 0,
// }
 
// // Reducer => 새로운 액션 값 추가 하고 싶으면 case지정해서 추가하기!
// export default function memberReducer(state = initialState, { type, payload }) {
//     switch(type) {
//         case MEMBER_INSERT:
//             return {
//                 ...state,
//                 members: state.members.concat({//concat 여러 문자열 하나로 연결
//                     id: payload.id,
//                     email: payload.email,
//                     nick: payload.nick,
//                     pw: payload.pw
//                 })
//             }
//         case MEMBER_UPDATE:
//             return {
//                 ...state,
//                 members: state.members.todos.map((members)=> 
//                 members.id === payload.id ? {...members, nick: payload.nick, pw: payload.pw} : members) 
//             }
//         case MEMBER_REMOVE: 
//         return {
//             ...state,
//             members: state.members.filter((members)=> members.id !== payload.id),
//         }
//         default:
//             return state;
//     }
// }