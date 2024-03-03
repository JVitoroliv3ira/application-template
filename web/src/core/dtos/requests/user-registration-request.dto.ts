import { z } from 'zod';

interface UserRegistrationRequestDTO {
  name: string;
  email: string;
  password: string;
}

const validationSchema = z.object({
  name: z.string()
    .min(5, { message: "O nome deve conter no mínimo 5 caracteres." })
    .max(60, { message: "O nome deve conter no máximo 60 caracteres." }),
  email: z.string()
    .email({ message: "O email informado é inválido." })
    .min(5, { message: "O email deve conter no mínimo 5 caracteres." })
    .max(80, { message: "O email deve conter no máximo 80 caracteres." }),
  password: z.string()
    .min(6, { message: "A senha deve conter no mínimo 6 caracteres." })
});

export default UserRegistrationRequestDTO;
export { validationSchema }
