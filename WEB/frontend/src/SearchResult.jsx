import { Card, CardContent, CardHeader, CardMedia, Container, Stack, Typography } from '@mui/material';
import { useState, useEffect, use } from "react"
import { useParams } from "react-router-dom"

export default function SearchResult() {
    const { searchedWord } = useParams();
    const [searchResults, setSearchResults] = useState([]);

    useEffect(() => {
        function fetchData() {
            fetch('http://localhost:3333/api/by-search/' + searchedWord)
                .then(response => response.json())
                .then(data => {
                    setSearchResults(data);
                })
                .catch(error => console.warn('Error fetching:', error));
        }

        fetchData();
    }, [searchedWord, setSearchResults]);

    return (
        <>
            <Container sx={{ maxWidth: '1100px', background: 'rgba(255,255,255,0.75)', borderRadius: '12px', padding: 2 }}>
                <Typography variant='h4' align='center' gutterBottom>
                    Keresés: "{searchedWord}"
                </Typography>
                <Stack sx={{ display: 'flex', flexDirection: 'row', flexWrap: 'wrap', gap: 2, alignItems: 'center', justifyContent: 'center' }}>
                    {searchResults.length != 0 ? searchResults.map((item, index) => {
                        return (
                            <Card key={index} sx={{ width: '40vh', cursor: 'pointer', borderRadius: '12px' }} onClick={() => navigate(`/streets/${item.szelessegi}/${item.hosszusagi}`)}>
                                <CardContent>
                                    <Typography variant='h5' gutterBottom sx={{ fontWeight: 'bold' }}>
                                        {(item.part + " - " + item.utca_nev + " (" + item.varos + ")")}
                                    </Typography>
                                    <Typography gutterBottom>
                                        {item.felirat}
                                    </Typography>
                                    <Typography sx={{ fontSize: '0.75rem', color: 'gray' }}>
                                        {item.szelessegi + ", " + item.hosszusagi}
                                    </Typography>
                                </CardContent>
                            </Card>
                        );
                    })
                        :
                        <Typography variant='h6'>
                            Nincs találat a keresésre
                        </Typography>
                    }
                </Stack>
            </Container>
        </>
    )
}