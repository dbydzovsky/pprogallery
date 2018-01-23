import * as b from 'bobril'
import * as bs from 'bobrilStrap'

export interface IData {
}

export interface IContext extends b.IBobrilCtx {
    data: IData;
}

export const create = b.createComponent<IData>({
    render(ctx: IContext, me: b.IBobrilNode) {
        me.className = "container";
        me.style = {
            minHeight: 600, width: "70%", backgroundColor: "", marginTop: 20,

        };
        me.children = [
            //Sign in
            bs.PageHeader({style: {fontFamily: "Georgia",}}, bs.H2({}, ['Sign in  ', bs.Small({}, ' - if you have an account')])),

            bs.Form({ horizontal: true }, [
                bs.FormGroup({}, [
                    bs.Label({ controlLabel: true, for: 'inputLogin', style: bs.colStyles(bs.Size.Sm)(2) }, 'Login'),
                    bs.Col({ size: bs.Size.Sm, span: 10 },
                        bs.InputText({ id: 'inputLogin', type: bs.InputTextType.Text, placeholder: 'Login' }))
                ]),
                bs.FormGroup({}, [
                    bs.Label({ controlLabel: true, for: 'inputPassword', style: bs.colStyles(bs.Size.Sm)(2) }, 'Password'),
                    bs.Col({ size: bs.Size.Sm, span: 10 },
                        bs.InputText({ id: 'inputPassword', type: bs.InputTextType.Password, placeholder: 'Password' }))
                ]),
                bs.FormGroup({}, [
                    bs.Col({ size: bs.Size.Sm, span: 10, offsets: [{ size: bs.Size.Sm, span: 2 }] },
                        bs.Checkbox({ label: { title: 'Remember me' } }))
                ]),
                bs.FormGroup({}, [
                    bs.Col({ size: bs.Size.Sm, span: 10, offsets: [{ size: bs.Size.Sm, span: 2 }] },
                        bs.Button({}, bs.A({href: '#/user/option', style: {textDecoration: "none", padding:15}},  'Sign In'))
                    )
                ])
            ]),

            // Register
            bs.PageHeader({style: {fontFamily: "Georgia",}}, bs.H2({}, ['Register  ', bs.Small({}, ' - if you have not an account')])),

            bs.Form({ horizontal: true }, [
                bs.FormGroup({}, [
                    bs.Label({ controlLabel: true, for: 'inputLogin', style: bs.colStyles(bs.Size.Sm)(2) }, 'Login'),
                    bs.Col({ size: bs.Size.Sm, span: 10 },
                        bs.InputText({ id: 'inputLogin', type: bs.InputTextType.Text, placeholder: 'Login' }))
                ]),
                bs.FormGroup({}, [
                    bs.Label({ controlLabel: true, for: 'inputEmail', style: bs.colStyles(bs.Size.Sm)(2) }, 'Email'),
                    bs.Col({ size: bs.Size.Sm, span: 10 },
                        bs.InputText({ id: 'inputEmail', type: bs.InputTextType.Email, placeholder: 'Email' }))
                ]),

                bs.FormGroup({}, [
                    bs.Label({ controlLabel: true, for: 'inputPassword', style: bs.colStyles(bs.Size.Sm)(2) }, 'Password'),
                    bs.Col({ size: bs.Size.Sm, span: 10 },
                        bs.InputText({ id: 'inputPassword', type: bs.InputTextType.Password, placeholder: 'Password' }))
                ]),

                bs.FormGroup({}, [
                    bs.Label({ controlLabel: true, for: 'inputPassword', style: bs.colStyles(bs.Size.Sm)(2) }, 'Confirm password'),
                    bs.Col({ size: bs.Size.Sm, span: 10 },
                        bs.InputText({ id: 'inputPassword', type: bs.InputTextType.Password, placeholder: 'Confirm password' }))
                ]),
                bs.FormGroup({}, [
                    bs.Col({ size: bs.Size.Sm, span: 10, offsets: [{ size: bs.Size.Sm, span: 2 }] },
                        bs.Checkbox({ label: { title: 'Accept something important' } }))
                ]),
                bs.FormGroup({}, [
                    bs.Col({ size: bs.Size.Sm, span: 10, offsets: [{ size: bs.Size.Sm, span: 2 }] },
                        bs.Button({ onClick: () => alert('Now you are in!')}, bs.A({href: '#/user/option', style: {textDecoration: "none", padding: 15}},  'Register'))
                    )
                ])
            ]),

        ]
    }
});