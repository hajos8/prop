import { Card, CardContent, CardHeader, CardMedia, Container, Stack, Typography } from '@mui/material';
import { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';

export default function Posters() {
    const longitude = useParams().long;
    const latitude = useParams().lat;

    const [plakatok, setPlakatok] = useState([]);

    useEffect(() => {
        function fetchData() {
            fetch(`http://localhost:3333/api/plakatok/by-lat/${latitude}/by-long/${longitude}`)
                .then(response => response.json())
                .then(data => {
                    setPlakatok(data);
                })
                .catch(error => console.warn('Error fetching:', error));
        }

        fetchData();
    }, [longitude, latitude])

    return (
        <>
            <Container sx={{ maxWidth: '1100px', background: 'rgba(255,255,255,0.75)', borderRadius: '12px', padding: 2 }}>
                <Typography variant='h4' align='center' gutterBottom>
                    Plakátok
                </Typography>
                <Stack sx={{ display: 'flex', flexDirection: 'row', flexWrap: 'wrap', gap: 2, alignItems: 'center', justifyContent: 'center' }}>
                    {plakatok.map((item, index) => {
                        return (
                            <Card key={index} sx={{ width: '40vh', cursor: 'pointer', borderRadius: '12px' }} onClick={() => navigate(`/streets/${item.szelessegi}/${item.hosszusagi}`)}>
                                <CardMedia>
                                    <img src={item.kep} alt={item.nev} style={{ height: '30vh' }} />
                                </CardMedia>
                                <CardContent>
                                    <Typography variant='h5' gutterBottom>
                                        {item.part}
                                    </Typography>
                                    <Typography>
                                        {item.felirat}
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