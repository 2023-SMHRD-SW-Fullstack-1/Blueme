import React, { useState } from "react";
import axios from "axios";

const AddMusicPage = () => {
  const [files, setFiles] = useState([]);

  const handleFileChange = (e) => {
    setFiles(e.target.files);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    const formData = new FormData();
    console.log("asd");
    // 여러 파일이므로 각각의 파일에 대해 append 실행
    for (let i = 0; i < files.length; i++) {
      formData.append("files", files[i]);
    }
    console.log("abc");
    try {
      const response = await axios.post(
        "http://localhost:8104/admin/addmusic",
        formData
      );
      console.log("eee");
      console.log(response.data); // log the response
    } catch (error) {
      console.error(error);
    }
  };

  return (
    <form onSubmit={handleSubmit}>
      {/* multiple 속성 추가하여 다중 선택 가능하게 함 */}
      <input type="file" onChange={handleFileChange} multiple />
      <button type="submit">Upload</button>
    </form>
  );
};

export default AddMusicPage;
