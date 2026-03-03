import { Card, CardContent, CardHeader, CardMedia, Container, Stack, Typography } from '@mui/material';
import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';

export default function Home() {
    const navigate = useNavigate();
    const [streets, setStreets] = useState([]);

    useEffect(() => {
        function fetchData() {
            fetch('http://localhost:3333/api/streets')
                .then(response => response.json())
                .then(data => {
                    setStreets(data);
                })
                .catch(error => console.warn('Error fetching:', error));
        }

        fetchData();
    }, [setStreets]);

    return (
        <>
            <Container sx={{ maxWidth: '1100px', background: 'rgba(255,255,255,0.75)', borderRadius: '12px', padding: 2 }}>
                <Typography variant='h4' align='center' gutterBottom>
                    Utcák
                </Typography>
                <Stack sx={{ display: 'flex', flexDirection: 'row', flexWrap: 'wrap', gap: 2, alignItems: 'center', justifyContent: 'center' }}>
                    {streets.map((item, index) => {
                        return (
                            <Card key={index} sx={{ width: '40vh', cursor: 'pointer', borderRadius: '12px' }} onClick={() => navigate(`/streets/${item.szelessegi}/${item.hosszusagi}`)}>
                                <CardMedia>
                                    <img src={item.kep} alt={item.nev} style={{ height: '30vh' }} />
                                </CardMedia>
                                <CardContent>
                                    <Typography variant='h5' gutterBottom>
                                        {item.nev}
                                    </Typography>
                                    <Typography variant='h6' gutterBottom>
                                        {item.varos} - {item.iranyitoszam}
                                    </Typography>
                                    <Typography color='secondary'>
                                        {item.szelessegi}, {item.hosszusagi}
                                    </Typography>
                                </CardContent>
                            </Card>
                        );
                    })}
                </Stack>
            </Container>
        </>
    )
}