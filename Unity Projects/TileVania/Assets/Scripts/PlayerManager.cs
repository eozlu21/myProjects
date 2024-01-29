using System;
using System.Collections;
using System.Collections.Generic;
using Unity.VisualScripting;
using UnityEngine;
using UnityEngine.InputSystem;

public class PlayerManager : MonoBehaviour
{
    private Vector2 moveInput;
    private Rigidbody2D myRigidbody2D;
    private Collider2D myBodyCollider2D;
    private Collider2D myFeetCollider2D;
    private float defaultGravityScale;
    [SerializeField] private float horizontalSpeed;
    [SerializeField] private float horizontalSpeedClimbing;
    [SerializeField] private float jumpSpeed;
    [SerializeField] private float climbSpeed;
    [SerializeField] private float deathJumpSpeed;
    [SerializeField] private GameObject bullet;
    [SerializeField] private Transform gun;
    private bool isClimbing;
    private Animator animator;
    private bool facingLeft;
    public bool isDead;
     


    private static readonly int IsRunning = Animator.StringToHash("isRunning");
    private static readonly int IsClimbing = Animator.StringToHash("isClimbing");

    void Start()
    {
        
        myRigidbody2D = GetComponent<Rigidbody2D>();
        animator = GetComponent<Animator>();
        myBodyCollider2D = GetComponent<CapsuleCollider2D>();
        myFeetCollider2D = GetComponent<BoxCollider2D>();
        defaultGravityScale = myRigidbody2D.gravityScale;
    }


    void Update()
    {
        if (isDead)
        {
            return;
        }

        Run();
        ClimbLadder();
        FlipSprite();
    }

    private void OnCollisionEnter2D(Collision2D other)
    {
        if (!other.gameObject.tag.Equals("Enemy") || isDead)
        {
            return;
        }

        Die();
    }

    public void Die()
    {
        isDead = true;
        animator.SetTrigger("hasDied");
        myRigidbody2D.velocity = new Vector2(myRigidbody2D.velocity.x, deathJumpSpeed);
        FindObjectOfType<GameSession>().ProcessPlayerDeath();
    }

    private void ClimbLadder()
    {
        // startClimbing
        if (!isClimbing)
        {
            if (!myBodyCollider2D.IsTouchingLayers(LayerMask.GetMask("Ladder")))
            {
                return;
            }

            if (moveInput.y <= 0)
            {
                return;
            }

            isClimbing = true;
        }

        // check if player is on a ladder
        if (!myBodyCollider2D.IsTouchingLayers(LayerMask.GetMask("Ladder")))
        {
            // check if player is still not affected by gravity
            if (myRigidbody2D.gravityScale != 0f)
            {
                return;
            }

            ExitClimbing();
            return;
        }

        if (myBodyCollider2D.IsTouchingLayers(LayerMask.GetMask("Ground")) && moveInput.y < 0)
        {
            ExitClimbing();
            return;
        }

        // check if player is not moving while on a ladder
        if (moveInput.magnitude == 0)
        {
            animator.speed = 0f;
        }
        else
        {
            animator.speed = 1f;
        }

        myRigidbody2D.gravityScale = 0f;
        animator.SetBool(IsClimbing, true);
        myRigidbody2D.velocity = new Vector2(moveInput.x * horizontalSpeedClimbing, moveInput.y * climbSpeed);
    }

    private void ExitClimbing()
    {
        myRigidbody2D.gravityScale = defaultGravityScale;
        animator.SetBool(IsClimbing, false);
        isClimbing = false;
    }

    private void FlipSprite()
    {
        if (moveInput.x == 0)
        {
            return;
        }

        transform.localScale = new Vector2(Mathf.Sign(myRigidbody2D.velocity.x), 1f);
    }

    private void Run()
    {
        if (moveInput.x != 0)
        {
            animator.SetBool(IsRunning, true);
        }
        else
        {
            animator.SetBool(IsRunning, false);
        }


        myRigidbody2D.velocity = new Vector2(moveInput.x * horizontalSpeed, myRigidbody2D.velocity.y);
    }

    void OnMove(InputValue value)
    {
        moveInput = value.Get<Vector2>();
    }

    void OnJump(InputValue value)
    {
        if (!value.isPressed)
        {
            return;
        }

        if (!myFeetCollider2D.IsTouchingLayers(LayerMask.GetMask("Ground")) &&
            !myBodyCollider2D.IsTouchingLayers(LayerMask.GetMask("Ladder"))) return;
        ExitClimbing();
        myRigidbody2D.velocity += new Vector2(0f, jumpSpeed);
    }

    void OnFire(InputValue value)
    {
        if (isDead)
        {
            return;
        }

        GameObject newBullet = Instantiate(bullet, gun.position, transform.rotation);
        
    }
}