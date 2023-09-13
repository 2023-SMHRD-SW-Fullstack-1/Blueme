const initialState = {
   isMini: true,
 };
 
 function player(state = initialState, action) {
   switch (action.type) {
     case 'TOGGLE_PLAYER':
       return { ...state, isMini: !state.isMini };
     default:
       return state;
   }
 }
 
 export default player;
 
 export function togglePlayer() {
    return {
      type: "TOGGLE_PLAYER",
    };
 }
 