const post = async <C, R> (endpoint: string, content: C, headers?: HeadersInit): Promise<R> => {
  const base = import.meta.env.VITE_BASE_API_URL;
  const url = `${base}/${endpoint}`;
  const response = await fetch(
    url,
    {
      method: 'POST',
      headers: { 'Content-Type': 'application/json', ...headers },
      body: JSON.stringify(content)
    }
  );

  return await response.json() as R;
}

export { post };
