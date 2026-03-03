const express = require('express');
const cors = require('cors');
const mysql = require('mysql2');

const app = express();
const port = 3333;

const connection = mysql.createConnection({
    host: 'localhost',
    user: 'root',
    password: '',
    database: 'propaganda',
});

app.use(cors());
app.use(express.json());

app.get('/api/streets', (req, res) => {
    const sql = `SELECT * FROM utcak;`; // skeleton only

    connection.query(sql, (err, results) => {
        if (err) {
            console.error('Error executing query:', err);
            return res.status(500).json({ message: 'Internal server error' });
        }
        else {
            return res.status(200).json(results);
        }
    });
});

app.get('/api/plakatok/by-lat/:lat/by-long/:long', (req, res) => {
    const lat = req.params.lat;
    const long = req.params.long;

    const sql = `
        SELECT plakatok.id, plakatok.part, plakatok.felirat, plakatok.kep 
        FROM utcak 
        INNER JOIN kihelyezesek ON utcak.szelessegi = kihelyezesek.szelessegi AND utcak.hosszusagi = kihelyezesek.hosszusagi 
        INNER JOIN plakatok ON kihelyezesek.plakat_id = plakatok.id
        WHERE abs(utcak.szelessegi - ?) <= 1e-2
        AND abs(utcak.hosszusagi - ?) <= 1e-2;`;

    connection.query(sql, [lat, long], (err, results) => {
        if (err) {
            console.error('Error executing query:', err);
            return res.status(500).json({ message: 'Internal server error' });
        }
        else {
            return res.status(200).json(results);
        }
    });
});

app.get('/api/by-search/:search_word', (req, res) => {
    const word = req.params.search_word;

    const sql = `
            SELECT utcak.szelessegi, utcak.hosszusagi, plakatok.id AS 'plakat_id', utcak.varos, utcak.nev AS 'utca_nev', plakatok.part, plakatok.felirat, plakatok.kep
            FROM utcak
            INNER JOIN kihelyezesek ON utcak.szelessegi = kihelyezesek.szelessegi AND utcak.hosszusagi = kihelyezesek.hosszusagi
            INNER JOIN plakatok ON kihelyezesek.plakat_id = plakatok.id
            WHERE utcak.nev LIKE ? OR utcak.varos LIKE ? OR plakatok.part LIKE ? OR plakatok.felirat LIKE ?;`;

    connection.query(sql, [`%${word}%`, `%${word}%`, `%${word}%`, `%${word}%`], (err, results) => {
        if (err) {
            console.error('Error executing query:', err);
            return res.status(500).json({ message: 'Internal server error' });
        }
        else {
            return res.status(200).json(results);
        }
    });
});

app.get('/api/kihelyezesek', (req, res) => {
    const sql = `
        SELECT utcak.szelessegi, utcak.hosszusagi, plakatok.id AS 'plakat_id', utcak.varos, utcak.nev AS 'utca_nev', utcak.iranyitoszam, utcak.jelleg, utcak.kep AS 'utca_kep', plakatok.part, plakatok.felirat, plakatok.kep AS 'plakat_kep'
        FROM utcak 
        INNER JOIN kihelyezesek ON utcak.szelessegi = kihelyezesek.szelessegi AND utcak.hosszusagi = kihelyezesek.hosszusagi 
        INNER JOIN plakatok ON kihelyezesek.plakat_id = plakatok.id;`;

    connection.query(sql, (err, results) => {
        if (err) {
            console.error('Error executing query:', err);
            return res.status(500).json({ message: 'Internal server error' });
        }
        else {
            return res.status(200).json(results);
        }
    });
});

app.post('/api/kihelyezesek', (req, res) => {
    const { latitude, longitude, plakat_id } = req.body;

    const sql = `INSERT INTO kihelyezesek (szelessegi, hosszusagi, plakat_id) VALUES (?, ?, ?);`;

    connection.query(sql, [latitude, longitude, plakat_id], (err, results) => {
        if (err) {
            console.error('Error executing query:', err);
            return res.status(500).json({ message: 'Internal server error' });
        }
        else {
            return res.status(201).json({ message: 'Kihelyezes created successfully' });
        }
    });
});

app.use((req, res) => {
    res.status(404).json({ message: 'Not found' });
});

app.listen(port, () => {
    console.log(`API listening on http://localhost:${port}`);
});