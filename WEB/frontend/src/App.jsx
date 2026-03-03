import { BrowserRouter, Routes, Route } from 'react-router-dom'
import './App.css'

import Home from './Home.jsx'
import Posters from './Posters.jsx'
import SearchResult from './SearchResult.jsx'

import MyMenu from './MyMenu.jsx';

function App() {

  return (
    <>

      <BrowserRouter>

        <MyMenu />

        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/streets/:lat/:long" element={<Posters />} />
          <Route path="/search/:searchedWord" element={<SearchResult />} />
        </Routes>
      </BrowserRouter>
    </>
  )
}

export default App