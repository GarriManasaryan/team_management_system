import React, { useEffect, useState } from 'react'
import axios, { AxiosHeaders, AxiosRequestHeaders } from 'axios';
import axiosConf from '../../axiosConf';
import { Button } from 'antd';
import { UserInterface } from './UserInterface';

function ListUsers() {
  
    useEffect(() => {
        getTasks();
    }, [])
  
    const getTasks = () => {
      axiosConf.get('/api/users')
        .then((response) => {
          console.log(response.data)
        })
    }

    const createUser = () => {
        const formValues: UserInterface = {
            name: "csac",
            email: "acsaa",
            role: "MANAGER"
        }
        axiosConf.post('/api/users', formValues)
        .then((response) => {
          console.log(response)
        })
    }

    return (
        <>
            <Button 
                className='dropdown-field-btn' 
                type='ghost' 
                onClick={()=> createUser()}
            >click me
            </Button>
        </>
    )
}

export default ListUsers