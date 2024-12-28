import express from 'express'

import {getNotes} from './database.js'

import cors from 'cors'

const app = express ()
app.use(cors())

app.get('/', (re, res)=> {
    return res.json("From Backend Side");
})

app.get("/notes",async (req,res) => {
    const notes = await getNotes()
    res.send(notes)
})

app.use((err, req, res, next) => {
    console.error(err.stack)
    res.status(500).send('something broken')
})
app.listen(8081,()=>{
    console.log('server is running on port 8081')
})




