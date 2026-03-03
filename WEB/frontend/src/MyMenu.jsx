import { AppBar, Box, Toolbar, IconButton, TextField, InputAdornment, Button } from "@mui/material";
import HomeIcon from '@mui/icons-material/Home';
import SearchIcon from '@mui/icons-material/Search';

import { useNavigate } from "react-router-dom";
import { useState } from "react";

export default function Navbar() {
    const [searchTerm, setSearchTerm] = useState("");
    const navigate = useNavigate();

    const handleNavigateToHomePage = () => {
        navigate('/');
    };

    const handleSearch = () => {
        if (searchTerm.trim().length < 3) {
            alert("3 karakter vagy annál többnek kell lennie!");
            return;
        }

        navigate('/search/' + searchTerm.trim());
    };

    const handleKeyDown = (e) => {
        if (e.key === 'Enter') {
            handleSearch();
        }
    };

    return (
        <AppBar
            position="fixed"
            color="default"
            elevation={1}
            sx={{
                backgroundColor: 'rgba(0,0,0,0.75)',
                borderBottom: '1px solid #e5e7eb',
                position: 'sticky',
                marginBottom: '1rem',
            }}
        >
            <Toolbar sx={{ px: { xs: 2, md: 4 } }}>
                <IconButton
                    size="large"
                    edge="start"
                    aria-label="home"
                    sx={{ color: 'white' }}
                    onClick={handleNavigateToHomePage}
                >
                    <HomeIcon />
                </IconButton>

                <Box sx={{ display: 'flex', alignItems: 'center', marginLeft: 'auto', gap: 1, width: { xs: '100%', sm: 'auto' }, maxWidth: 520 }}>
                    <TextField
                        sx={{ backgroundColor: 'white', borderRadius: '12px' }}
                        fullWidth
                        size="small"
                        type="search"
                        variant="outlined"
                        value={searchTerm}
                        onChange={e => setSearchTerm(e.target.value)}
                        onKeyDown={handleKeyDown}
                    />
                    <IconButton style={{ backgroundColor: 'lightblue' }} variant="contained" onClick={handleSearch} sx={{ whiteSpace: 'nowrap' }}>
                        <SearchIcon style={{ color: 'white' }} />
                    </IconButton>
                </Box>
            </Toolbar>
        </AppBar>
    );
}