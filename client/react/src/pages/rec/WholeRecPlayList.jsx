import React, { useEffect } from 'react'
import axios from 'axios'

const WholeRecPlayList = () => {

    const id = localStorage.getItem('id')

    useEffect(() => {
        axios.get(`http://172.30.1.27:8104/recMusiclist/${id}`)
        .then((res) => {
            console.log(res);
        })
        .catch((err) => console.log(err))
    }, [])
  return (
    <div>

    </div>
  )
}

export default WholeRecPlayList