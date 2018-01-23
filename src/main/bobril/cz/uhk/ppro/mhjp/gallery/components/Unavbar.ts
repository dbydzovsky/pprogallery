import * as b from 'bobril';
import * as bs from 'bobrilstrap';

export interface IData {
}

export interface IContext extends b.IBobrilCtx {
    data: IData;
}

export const create = b.createComponent<IData>({
    render(ctx: IContext, me: b.IBobrilNode) {
        me.className = "container";
        me.style = {
            minHeight: 40, fontSize: 17, width: "55%", fontFamily: "Georgia", textAlign: "center", backgroundColor: "",
        };
        me.children = [
            bs.Row({style:{}}, [
                bs.Pills({style: bs.typography.textCenter}, [
                    bs.Pill({}, bs.A({href: '#/user'}, 'Home')),
                    bs.Pill({}, bs.A({href: '#/user/trendy'}, "Trendy")),

                    bs.Dropdown(
                        {button: {label: 'Gallery', variant: bs.ButtonVariant.DropdownNav}},
                        bs.DropdownMenu({style: {}}, [
                            bs.DropdownItem({}, bs.A({href: '#/user/gallery'}, 'New gallery ')),
                            bs.DropdownItem({separator: true}),
                            bs.DropdownItem({}, bs.A({href: '#/user/gallery'}, 'Gallery name')),
                            bs.DropdownItem({}, bs.A({href: '#/user/gallery'}, 'Gallery name')),
                        ])
                    ),
                    bs.Pill({}, bs.A({href: '#/user/find'}, 'Find')),
                    bs.Pill({}, bs.A({href: '#/user/option'}, 'User')),
                    bs.Pill({}, bs.A({href: '#/'}, 'Logout')),
                ])
            ])
        ]
    }
});
