// import axios from 'axios';

// const fetchAuth = async (fetchData) => {
//   const method = fetchData.method;
//   const url = fetchData.url;
//   const data = fetchData.data;
//   const header = fetchData.header;

//   try {
//     let response =
//       (method === 'get' && (await axios.get(url, header))) ||
//       (method === 'post' && (await axios.post(url, data, header))) ||
//       (method === 'put' && (await axios.put(url, data, header))) ||
//       (method === 'delete' && (await axios.delete(url, header)));

//     if(response && response.data.error) {
//       console.log(response.data.error);
//       alert("이메일과 비밀번호를 확인해주세요");
//       return null;
//     }

//     if (!response) {
//       alert("실패!");
//       return null;
//     }

//     return response;

//   } catch(err) {

//     if(axios.isAxiosError(err)) {
//         if(err.response){
//             console.log(err.response.data);
//             alert("실패!");
//             return null; 
//         }
//     }

//     console.log(err);
//     alert("실패!");
    
//    return null;
//   }
// }

// const GET = async function(url, header){
//    const response = await fetchAuth({ method: 'get', url: url ,header:header});
//    return response
// }

// const POST = async function(url,data ,header){
//    const response= await fetchAuth({ method: 'post', url:url ,data:data ,header:header})
//    return response
// };

// const PUT=async function( url,data ,header){
//    const response= await fetchAuth({ method:'put',url:url,data:data ,header:header})
//    return response
// };

// const DELETE=async function( url ,header){
//    const response= await fetchAuth({ method:'delete',url:url ,header:header})
   
// return response

// };
// export { GET, POST, PUT, DELETE };
