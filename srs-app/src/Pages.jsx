import React from 'react';
import './App.css'
import {Routes, Route, BrowserRouter} from 'react-router-dom';
import Decks from './Decks/Decks.jsx'
import Home from './Home/Home.jsx'
import FlashCard from './Decks/FlashCard.jsx';

function Pages() {
  
  return (
    <>
      <BrowserRouter>
        <Routes>
          <Route path='/' element={<Home />}></Route>
          <Route path='/decks' element={<Decks />}></Route>
          <Route path='/flashcard' element={<FlashCard />}></Route>
        </Routes>
      </BrowserRouter>
    </>
    
  );
}

export default Pages
