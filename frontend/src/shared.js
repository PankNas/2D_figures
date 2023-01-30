function get_host() {
    const params = new Proxy(new URLSearchParams(window.location.search), {
        get: (searchParams, prop) => searchParams.get(prop),
    });

    let address;

    if (params.address) {
        address = `http://${params.address}`;
    } else {
        address = window.location.origin;
    }
    return address;
}

export default get_host;